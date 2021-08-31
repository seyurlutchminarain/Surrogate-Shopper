package com.example.surrogateshopper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class updateVprofile extends AppCompatActivity {
    private Button Updateusername,Updatepassword;
    private String  type,ID;
    private EditText user,Password,CPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vprofile);

        setupUI();

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
                    PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/UpdateVUsername.php");
                    r.DoReq(updateVprofile.this, cv, new Processor() {
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
                        PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/UpdateVPass.php");
                        r.DoReq(updateVprofile.this, cv, new Processor() {
                            @Override
                            public void converter(String res) {
                                Toast.makeText(updateVprofile.this,"Password Updated!",Toast.LENGTH_LONG).show();
                                Password.setText("");
                                CPassword.setText("");
                            }
                        });
                    }
                }
            }
        });

    }
    private void setupUI(){
        Updateusername = (Button)findViewById(R.id.btusername);
        Updatepassword = (Button)findViewById(R.id.btChangePassword);
        user = (EditText)findViewById(R.id.etChangeusername);
        Password = (EditText)findViewById(R.id.etNewPassword);
        CPassword = (EditText)findViewById(R.id.etConfirmPassword);
        type = getIntent().getStringExtra("type");
        ID = getIntent().getStringExtra("volid");

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
        Toast.makeText(updateVprofile.this,x,Toast.LENGTH_LONG).show();
    }
}