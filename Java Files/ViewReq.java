package com.example.surrogateshopper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewReq extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private LinearLayout Taken,NotTaken;
    private String VOL_ID,REQ_ID,R_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_req);
        setupUI();
        showreq();
    }
    private void setupUI(){
        Taken = (LinearLayout)findViewById(R.id.mytaken);
        NotTaken = (LinearLayout)findViewById(R.id.nottaken);
        VOL_ID = getIntent().getStringExtra("volid");
    }

    private void showreq(){
        PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/AllReqs.php");
        ContentValues cv = new ContentValues();
        r.DoReq(ViewReq.this, cv, new Processor() {
            @Override
            public void converter(String res) {
                processJson(res);
            }
        });
    }
    private void processJson(String json){
        try {
            Taken.removeAllViews();
            NotTaken.removeAllViews();
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                final JSONObject j = jsonArray.getJSONObject(i);
                final String volid = j.getString("VOL_ID");
                final String item = j.getString("REQ_ITEMS");


                final TextView req = new TextView(this);
                req.setTextColor(Color.parseColor("#0CFFFF"));

                TextView space = new TextView(this);

                req.setText(item);
                if(volid.equals(VOL_ID)){
                    Taken.addView(req);
                    Taken.addView(space);
                }
                else if(volid.equals("null")){
                    NotTaken.addView(req);
                    NotTaken.addView(space);
                }

                req.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            R_ID = j.getString("R_ID");
                            REQ_ID = j.getString("REQ_ID");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        PopupMenu popupMenu = new PopupMenu(ViewReq.this,v);
                        popupMenu.setOnMenuItemClickListener(ViewReq.this);

                        if(volid.equals(VOL_ID)){
                            popupMenu.inflate(R.menu.vol_untake);
                        }
                        else{
                            popupMenu.inflate(R.menu.vol_pop);
                        }
                        popupMenu.show();
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
               TakeReq(R_ID);
                return true;
            case R.id.title2:
                ViewReqLoc(REQ_ID);
                return true;
            case R.id.title3:
                UnTakeReq(R_ID);
                return true;
            default:
                return false;
        }
    }
    private void TakeReq(String x){
        PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/TakeReq.php");
        ContentValues cv = new ContentValues();
        cv.put("r_id",x);
        cv.put("vol_id",VOL_ID);
        r.DoReq(ViewReq.this, cv, new Processor() {
            @Override
            public void converter(String res) {
                showreq();//refreshes the page;
            }
        });
    }
    private void UnTakeReq(String x){
        PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/UnTakeReq.php");
        ContentValues cv = new ContentValues();
        cv.put("r_id",x);
        r.DoReq(ViewReq.this, cv, new Processor() {
            @Override
            public void converter(String res) {
                showreq();//refreshes the page;
            }
        });
    }
    private void ViewReqLoc(String id){
        Intent location = new Intent(ViewReq.this,ShowReqLoc.class);
        location.putExtra("req_id",id);
        startActivity(location);
    }
}