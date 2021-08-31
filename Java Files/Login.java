package com.example.surrogateshopper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
   
    String hashed,username, Password,value;
    private TextView Name, password;
    private Button Login, Sign_up;
    private CheckBox show_hide;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setupUI();
        //tells if user clicked request or volunteer
         value = getIntent().getStringExtra("RV");

        show_hide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    show_hide.setText("Hide Password");
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    show_hide.setText("Show Password");
                }
            }
        });


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = Name.getText().toString();
                Password = password.getText().toString();
                hashed = String.valueOf(Password.hashCode());
                ContentValues cv = new ContentValues();
                cv.put("name", username);
                cv.put("table",value);
                PhpReq r = new PhpReq("https://lamp.ms.wits.ac.za/home/s2088960/Validate.php");
                if (validate(username, Password)) {
                    ShowTost();
                }
                else {

                    if(value.equals("Requester")) {
                        r.DoReq(Login.this, cv, new Processor() {
                            @Override
                            public void converter(String res) {
                                isvalid(res,RProfile.class);
                            }
                        });
                    }
                    else{
                        r.DoReq(Login.this, cv, new Processor() {
                            @Override
                            public void converter(String res) {
                                isvalid(res,VProfile.class);
                            }
                        });
                    }

                }
            }
        });

        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sig=new Intent(Login.this,Sign_up.class);
                sig.putExtra("value",value);
                startActivity(sig);
            }
        });

    }

    //assigns widgets on layout to code
    private void setupUI() {
        Name = (EditText) findViewById(R.id.etUserName);
        password = (EditText) findViewById(R.id.etPassword);
        Login = (Button) findViewById(R.id.btLogin);
        Sign_up = (Button) findViewById(R.id.btSignUp);
        show_hide = (CheckBox) findViewById(R.id.chshowhidepword);
    }

    //show toast to display incomplete fields
    private void ShowTost() {
        Toast.makeText(com.example.surrogateshopper.Login.this, "Please Fill in all Fields", Toast.LENGTH_SHORT).show();
    }

    //checks if all EditText are not empty.
    private boolean validate(String username, String Password) {

        if (username.isEmpty() || Password.isEmpty()) {
            return  true;
        } else {
            return  false;
        }

    }

    public void isvalid(String r,Class<?> cls) {
        if(r.equals(hashed)) {

                Intent p = new Intent(Login.this,cls);
                p.putExtra("name", username);
                p.putExtra("type", value);
                startActivity(p);
        }
        else{
            Name.setText("");
            password.setText("");
            Toast.makeText(Login.this, "Username or Password Incorrect", Toast.LENGTH_SHORT).show();
        }
    }



}

