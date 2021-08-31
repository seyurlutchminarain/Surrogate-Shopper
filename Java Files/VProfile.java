package com.example.surrogateshopper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VProfile extends AppCompatActivity {
    private Button UpdateProfile,ViewRequests,Mymessages;
    TextView name,user_name;
    private String username,type,VOL_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vprofile);
        assignUI();
        getinfo();

        UpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Update = new Intent(VProfile.this, updateVprofile.class);
                Update.putExtra("volid",VOL_ID);
                Update.putExtra("type",type);
                startActivity(Update);
            }
        });

        ViewRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ViewReq = new Intent(VProfile.this, com.example.surrogateshopper.ViewReq.class);
                ViewReq.putExtra("volid",VOL_ID);
                ViewReq.putExtra("type",type);
                startActivity(ViewReq);
            }
        });

        Mymessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent message = new Intent(VProfile.this,MyMessages.class);
                message.putExtra("volid",VOL_ID);
                startActivity(message);
            }
        });

    }

    private void assignUI()
    {
        Mymessages = (Button) findViewById(R.id.btmess);
        UpdateProfile = (Button) findViewById(R.id.btVolUpdate);
        ViewRequests = (Button) findViewById(R.id.btAvailableRequests);
        name = (TextView) findViewById(R.id.etVolName);
        user_name = (TextView) findViewById(R.id.etVolUserName);
        username = getIntent().getStringExtra("name");
        type = getIntent().getStringExtra("type");
    }

    private void getinfo(){
        PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/userinfo.php");
        ContentValues cv = new ContentValues();
        cv.put("name",username);
        cv.put("table",type);
        r.DoReq(VProfile.this, cv, new Processor() {
            @Override
            public void converter(String res) {
                processJson(res);
            }
        });
       
    }
    private void processJson(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject j = jsonArray.getJSONObject(i);
                name.setText(j.getString("VOL_FNAME") +" "+ j.getString("VOL_LNAME"));
                user_name.setText("Username: "+ j.getString("VOL_USERNAME"));
                VOL_ID = j.getString("VOL_ID");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}