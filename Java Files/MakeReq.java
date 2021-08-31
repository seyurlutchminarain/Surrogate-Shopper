package com.example.surrogateshopper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MakeReq extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    LinearLayout Taken,Nottaken;

    private ArrayList<String> ITEMS = new ArrayList<String>();
    private String username,type,Item,ID,R_ID,VOL_ID;
    private EditText items;
    private Button add,request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_req);
         username = getIntent().getStringExtra("name");
         type = getIntent().getStringExtra("type");
         ID = getIntent().getStringExtra("id");

         setupUI();
         getreq();
         add.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Item=items.getText().toString();
                 if (isempty(Item)) {
                     Toast.makeText(MakeReq.this,"Please type an item",Toast.LENGTH_SHORT).show();
                 }
                 else{
                     ITEMS.add(Item);
                     items.setText("");
                 }
             }
         });

         request.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(ITEMS.size()==0){
                     Toast.makeText(MakeReq.this,"No Items Requested",Toast.LENGTH_SHORT).show();
                 }
                 else{
                    String requested = getitems();
                    ITEMS.clear();//clears the arraylist
                    ContentValues cv = new ContentValues();
                    cv.put("id",ID);
                    cv.put("items",requested);
                    PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/MakeReq.php");
                    r.DoReq(MakeReq.this, cv, new Processor() {
                        @Override
                        public void converter(String res) {
                                getreq();
                        }
                    });
                 }
             }
         });
    }
    private void setupUI(){
        items=(EditText)findViewById(R.id.etItems);
        add=(Button)findViewById(R.id.btAddItems);
        request=(Button)findViewById(R.id.btRequest);
        Taken = (LinearLayout)findViewById(R.id.Taken);
        Nottaken = (LinearLayout)findViewById(R.id.NotTaken);
    }
    private boolean isempty(String item){
        if(item.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
    private void getreq(){
        PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/Requests.php");
        ContentValues cv = new ContentValues();
        cv.put("id",ID);
        r.DoReq(MakeReq.this, cv, new Processor() {
            @Override
            public void converter(String res) {
                showrequests(res);
            }
        });
    }
    private void showrequests(String json){
        try {
            Taken.removeAllViews();
            Nottaken.removeAllViews();
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0 ;i<jsonArray.length();i++){
                final JSONObject j = jsonArray.getJSONObject(i);
                String items = j.getString("REQ_ITEMS");
                String taken = j.getString("REQ_TAKEN");
                final TextView ite = new TextView(this);
                ite.setText(items);

                ite.setTextColor(Color.parseColor("#0CFFFF"));
                TextView space = new TextView(this);


                if(taken.equals("0")){
                    Nottaken.addView(ite);
                    Nottaken.addView(space);
                }
                else{
                    Taken.addView(ite);
                    Taken.addView(space);

                }

                ite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            R_ID = j.getString("R_ID");
                            VOL_ID = j.getString("VOL_ID");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        PopupMenu popup = new PopupMenu(MakeReq.this,v);
                        popup.setOnMenuItemClickListener(MakeReq.this);
                            if(VOL_ID.equals("null")){
                                popup.inflate(R.menu.deletepopup);
                            }
                            else{
                                popup.inflate(R.menu.popup_menu);
                            }
                        popup.show();

                    }
                });


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    //loops through array and adds the items into a single string
    private String getitems(){
        String requested = "";
        requested += ITEMS.get(0) ;
        for(int i =1; i<ITEMS.size();i++){
            if(!isempty(ITEMS.get(i))){
                requested +=","+ ITEMS.get(i);
            }
        }
        return requested;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
       switch (item.getItemId()){
           case R.id.title1:
               SendMessage(VOL_ID);
               return true;
           case R.id.title2:
            DeleteReq(R_ID);
               return true;
           default:
               return false;
       }
    }
    private void SendMessage(String volid){
        Intent message = new Intent(MakeReq.this,SendMessage.class);
        message.putExtra("Volid",volid);
        message.putExtra("req_ID",ID);
        message.putExtra("name",username);
        startActivity(message);

    }
    private void DeleteReq(String reqid){
        ContentValues cv = new ContentValues();
        cv.put("rid",reqid);
        PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/DeleteReq.php");
        r.DoReq(MakeReq.this, cv, new Processor() {
            @Override
            public void converter(String res) {
                getreq();//refreshes the list
            }
        });
    }
}
