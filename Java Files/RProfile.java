package com.example.surrogateshopper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RProfile extends AppCompatActivity {

    private Button ReqUpdate, MakeReq;
    private String name, type,uname,loc,Fname,Lname,ID;
    private TextView etreqshowname,username,Loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rprofile);

        assignUI();
        getinfo();

        ReqUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(RProfile.this, updateRProfile.class);
                update.putExtra("name",name);
                update.putExtra("type",type);
                update.putExtra("id",ID);
                startActivity(update);
            }
        });

        MakeReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MakeReq = new Intent(RProfile.this,MakeReq.class);
                MakeReq.putExtra("name",name);
                MakeReq.putExtra("type",type);
                MakeReq.putExtra("id",ID);
                startActivity(MakeReq);

            }
        });



    }

    private void assignUI() {
        ReqUpdate = (Button) findViewById(R.id.button11);
        MakeReq = (Button) findViewById(R.id.button10);
        etreqshowname = (TextView) findViewById(R.id.etReqShowName);
        username = (TextView) findViewById(R.id.showReqUser);
        Loc = (TextView)findViewById(R.id.etReqLocation);
        name = getIntent().getStringExtra("name");
        type = getIntent().getStringExtra("type");
    }
    public void getinfo(){
        PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/userinfo.php");
        ContentValues cv = new ContentValues();
        cv.put("name",name);
        cv.put("table",type);
        r.DoReq(RProfile.this, cv, new Processor() {
            @Override
            public void converter(String res) {
                ProcessJson(res);
            }
        });
    }
    public void ProcessJson(String json){
        try {
            JSONArray jsonArray=new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject j =jsonArray.getJSONObject(i);
                ID = j.getString("REQ_ID");
                uname = j.getString("REQ_USERNAME");
                Fname = j.getString("REQ_FNAME");
                Lname = j.getString("REQ_LNAME");
                loc = j.getString("REQ_LOCATION");
                etreqshowname.setText(Fname+" "+Lname);
                username.setText("UserName: "+uname);
                Loc.setText(loc);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}