package com.example.surrogateshopper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowReqLoc extends AppCompatActivity {
    private Button viewloc;
    private TextView Name,Username;
    String REQ_ID,Loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_req_loc);
        setupUI();
        getinfo();

        viewloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+Loc));
                startActivity(add);
            }
        });
    }
    private void setupUI(){
        Name = (TextView)findViewById(R.id.etReqShowName);
        Username = (TextView)findViewById(R.id.showReqUser);
        REQ_ID = getIntent().getStringExtra("req_id");
        viewloc = (Button)findViewById(R.id.btLoc) ;
    }
    private void getinfo(){
        PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/Requester.php");
        ContentValues cv = new ContentValues();
        cv.put("req_id",REQ_ID);
        r.DoReq(ShowReqLoc.this, cv, new Processor() {
            @Override
            public void converter(String res) {
                ProcessJson(res);
            }
        });
    }
    private void ProcessJson(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject j =jsonArray.getJSONObject(i);
                Name.setText(j.getString("REQ_FNAME")+" "+j.getString("REQ_LNAME"));
                Username.setText(j.getString("REQ_USERNAME"));
                Loc = j.getString("REQ_LOCATION");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}