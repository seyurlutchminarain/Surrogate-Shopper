package com.example.surrogateshopper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;
import java.util.ArrayList;

public class MyMessages extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    LinearLayout mymessages;
    String REQ_ID, VOL_ID,M_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);
        mymessages = (LinearLayout)findViewById(R.id.mymess);
        VOL_ID = getIntent().getStringExtra("volid");
       getmessages();



    }

    private void getmessages(){
        PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/getmess.php");
        ContentValues cv = new ContentValues();
        cv.put("vol_id",VOL_ID);
        r.DoReq(MyMessages.this, cv, new Processor() {
            @Override
            public void converter(String res) {
            ProcessJson(res);
            }
        });
    }


    private void ProcessJson(String json){
        try {
            mymessages.removeAllViews();
            JSONArray jsonArray = new JSONArray(json);
            for (int i=0;i<jsonArray.length();i++){
                final JSONObject j = jsonArray.getJSONObject(i);
                REQ_ID= j.getString("REQ_ID");
               ProcessMessages pm = new ProcessMessages(this);
               pm.getMessage(j);
               pm.setBackgroundResource(R.drawable.button);
               
                mymessages.addView(pm);
                pm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            M_ID = j.getString("MSG_ID");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        PopupMenu popup = new PopupMenu(MyMessages.this,v);
                        popup.setOnMenuItemClickListener(MyMessages.this);
                        popup.inflate(R.menu.deletemess);
                        popup.show();
                    }

                });

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.title1:
                deleteMess(M_ID);
                return true;
            default:
                return false;
        }
    }
    private void deleteMess(String i){
        PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/deletemess.php");
        ContentValues cv = new ContentValues();
        cv.put("msg_id",i);
        r.DoReq(MyMessages.this, cv, new Processor() {
            @Override
            public void converter(String res) {
                getmessages();//refreshes the message list
            }
        });
    }
}