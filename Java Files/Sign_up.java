package com.example.surrogateshopper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Sign_up extends AppCompatActivity {
    private TextView FName, LName, Pass, ConPass, DOB,UserName;

    String First, Last, Password, Confirm, birth, Loc, user, type;

    private Button SignUp,map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setupUI();
        setloc();
        type = getIntent().getStringExtra("value");//Gets the type of user
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setstrings();

               if (CheckFields(First,Last,Password,Confirm,birth,user,Loc)){

                   showError("Please fill in all fields!");
                }
                else
                {
                    if (!validatePass(Password,Confirm))
                    {
                        showError("Passwords do not match!");
                    }
                    else
                    {
                           if(type.equals("Requester")){
                               ContentValues cv = new ContentValues();
                               pairs(cv);
                               PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/Rsignup.php");
                               r.DoReq(Sign_up.this, cv, new Processor() {
                                   @Override
                                   public void converter(String res) {
                                       validate(res,RProfile.class);
                                   }
                               });
                           }
                           else{
                               ContentValues cv = new ContentValues();
                               pairs(cv);
                               PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/Vsignup.php");
                               r.DoReq(Sign_up.this, cv, new Processor() {
                                   @Override
                                   public void converter(String res) {
                                       validate(res,VProfile.class);
                                   }
                               });
                           }

                    }
                }


            }
        });

    }




    private void setupUI()
    {
        FName = (EditText) findViewById(R.id.etFName);
        LName = (EditText) findViewById(R.id.etLName);
        Pass = (EditText) findViewById(R.id.etPassword);
        ConPass = (EditText) findViewById(R.id.etConPass);
        DOB = (EditText) findViewById(R.id.etDOB);
        SignUp = (Button) findViewById(R.id.btnSignUp);
        UserName = (EditText) findViewById(R.id.etUsername);

    }
    private void setstrings(){
        First = FName.getText().toString();
        Last = LName.getText().toString();
        Password = Pass.getText().toString();
        Confirm = ConPass.getText().toString();
        birth = DOB.getText().toString();
        user=UserName.getText().toString();
    }

    private boolean validatePass(String Pass1,String Pass2)//Checks if passwords match
    {
        if (Pass1.equals(Pass2))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    private boolean CheckFields(String First,String Last, String Pass, String Con, String DOB,String user,String loc)//Checks if all fields are filled in
    {
        if (First.isEmpty() || Last.isEmpty() || Pass.isEmpty() || Con.isEmpty() || DOB.isEmpty() || user.isEmpty() || loc.isEmpty()){
            return true;
        }
        else
        {

            return false;
        }
    }

    private void showError(String message)//Used to Display messages
    {

        Toast.makeText(com.example.surrogateshopper.Sign_up.this,message,Toast.LENGTH_SHORT).show();
    }

   private void pairs(ContentValues c){
        if(type.equals("Requester")) {
            c.put("name", user);
            c.put("fname", First);
            c.put("lname", Last);
            c.put("location", Loc);
            c.put("pword", String.valueOf(Password.hashCode()));
            c.put("dob", birth);
        }
        else{
            c.put("name",user);
            c.put("fname",First);
            c.put("lname",Last);
            c.put("pword",String.valueOf(Password.hashCode()));
            c.put("dob",birth);
        }
   }
    public void validate(String r,Class<?> cls){
       if(r.equals("false")){
           showError("UserName Already Taken!");
           UserName.setText("");
       }
       else{
               Intent p = new Intent(Sign_up.this,cls);
               p.putExtra("name", user);
               p.putExtra("type", type);
               startActivity(p);
       }

    }
    //creates an object Location and uses it to get the phones location.
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
            showError("Please Enable GPS!");
        }

    }


}
