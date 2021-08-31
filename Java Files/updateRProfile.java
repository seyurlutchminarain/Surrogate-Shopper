package com.example.surrogateshopper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class updateRProfile extends AppCompatActivity{

    private Button Updateusername,Updatepassword,Updatelocation;
    private String username, type,Loc,ID;
    private EditText user,Password,CPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_rprofile);
        setupUI();
        setloc();

        Updateusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = user.getText().toString();
                if(name.isEmpty()){
                    ShowMessage("Please Enter a new UserName ");
                }
                else{
                    ContentValues cv = new ContentValues();
                    cv.put("id",ID);
                    cv.put("new_username",name);
                    PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/UpdateRUsername.php");
                    r.DoReq(updateRProfile.this, cv, new Processor() {
                        @Override
                        public void converter(String res) {
                           ShowMessage(res);
                           user.setText("");

                        }
                    });
                }
            }
        });

        Updatepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = Password.getText().toString();
                String y = CPassword.getText().toString();
                if(isempty(x,y)){
                    ShowMessage("Please enter a new Password!");
                }
                else{
                    if(!ValidatePass(x,y)){
                       ShowMessage("Passwords do not match");
                       Password.setText("");
                       CPassword.setText("");
                    }
                    else {
                        ContentValues cv = new ContentValues();
                        String p = String.valueOf(x.hashCode());
                        cv.put("pword",p);
                        cv.put("id",ID);
                        PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/UpdatePass.php");
                        r.DoReq(updateRProfile.this, cv, new Processor() {
                            @Override
                            public void converter(String res) {
                                Toast.makeText(updateRProfile.this,"Password Updated!",Toast.LENGTH_LONG).show();
                                Password.setText("");
                                CPassword.setText("");
                            }
                        });
                    }
                }
            }
        });

        Updatelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues cv = new ContentValues();
                cv.put("loc",Loc);
                cv.put("id",ID);
                PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/UpdateLoc.php");
                r.DoReq(updateRProfile.this, cv, new Processor() {
                    @Override
                    public void converter(String res) {
                        ShowMessage("Location Updated!");
                    }
                });
            }
        });

    }
    private void setupUI(){
        Updateusername = (Button)findViewById(R.id.btusername);
        Updatepassword = (Button)findViewById(R.id.btChangePassword);
        Updatelocation = (Button)findViewById(R.id.btUpdateLocation);
        user = (EditText)findViewById(R.id.etChangeusername);
        Password = (EditText)findViewById(R.id.etNewPassword);
        CPassword = (EditText)findViewById(R.id.etConfirmPassword);
        username = getIntent().getStringExtra("name");
        type = getIntent().getStringExtra("type");
        ID = getIntent().getStringExtra("id");

    }



    private void setloc(){

        Location g = new Location(getApplicationContext());
        android.location.Location l=g.getlocation();
        Geocoder geocoder = new Geocoder(getApplicationContext(),Locale.ENGLISH);
        String res;
        if(l!=null){
            try {
                List<Address> addresses = geocoder.getFromLocation(l.getLatitude(),l.getLongitude(),1);
                if(addresses.size()>0){
                    Address address = addresses.get(0);
                    res = address.getAddressLine(0) +" , "+address.getAdminArea();
                    Loc=res;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else{
            Toast.makeText(updateRProfile.this,"Please Enable GPS!",Toast.LENGTH_LONG).show();
        }
    }
    private boolean isempty(String x,String y){
        if(x.isEmpty() || y.isEmpty()){
            return true;
        }
        else {
            return false;
        }
    }
    private boolean ValidatePass(String x,String y){
        if(x.equals(y)){
            return true;
        }
        else{
            return false;
        }
    }
    private void ShowMessage(String x){
        Toast.makeText(updateRProfile.this,x,Toast.LENGTH_LONG).show();
    }

}
