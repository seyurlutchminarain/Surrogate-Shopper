package com.example.surrogateshopper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SendMessage extends AppCompatActivity {

    private TextView volname,vol_username;
    private EditText messages;
    private Button SendMessage;
    private String ID,VOL_ID,username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        setupUI();
        setVolinfo();
        SendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m = messages.getText().toString();
                if(isempty(m)){
                    Toast.makeText(SendMessage.this,"Please type a Message",Toast.LENGTH_SHORT).show();
                }
                else{
                    ContentValues cv = new ContentValues();
                    cv.put("message",m);
                    cv.put("vol_id",VOL_ID);
                    cv.put("req_id",ID);
                    cv.put("name",username);
                    PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/SendMessage.php");
                    r.DoReq(SendMessage.this, cv, new Processor() {
                        @Override
                        public void converter(String res) {
                            Toast.makeText(SendMessage.this,"Message Sent",Toast.LENGTH_SHORT).show();
                            messages.setText("");
                        }
                    });
                }
            }
        });

    }
    private void setupUI(){
        messages = (EditText)findViewById(R.id.etMessage);
        SendMessage = (Button)findViewById(R.id.btSend);
        volname = (TextView)findViewById(R.id.etVolName);
        vol_username = (TextView)findViewById(R.id.etVolUserName);
        ID = getIntent().getStringExtra("req_ID");
        VOL_ID = getIntent().getStringExtra("Volid");
        username = getIntent().getStringExtra("name");
    }
    private boolean isempty(String m){
        if(m.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
    private void setVolinfo(){
        PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/VMessage.php");
        ContentValues cv =new ContentValues();
        cv.put("vol_id",VOL_ID);
        r.DoReq(SendMessage.this, cv, new Processor() {
            @Override
            public void converter(String res) {
                processJson(res);
            }
        });
    }
    private void processJson(String json){
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject j = jsonArray.getJSONObject(i);
                volname.setText(j.getString("VOL_FNAME") +" "+ j.getString("VOL_LNAME"));
                vol_username.setText(j.getString("VOL_USERNAME"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}