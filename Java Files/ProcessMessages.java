package com.example.surrogateshopper;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.icu.lang.UScript;
import android.os.TestLooperManager;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ProcessMessages extends LinearLayout {
    TextView message,Username;


    public ProcessMessages(Context context) {
         super(context);
         setOrientation(LinearLayout.HORIZONTAL);
         message = new TextView(context);


         Username = new TextView(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,0,0,40);
         setPadding(35,35,35,35);
         setLayoutParams(lp);
        LinearLayout hor = new LinearLayout(context);

        hor.setOrientation(HORIZONTAL);
        addView(hor);
        LinearLayout ver = new LinearLayout(context);
        ver.setOrientation(VERTICAL);

        hor.addView(ver);
        message.setPadding(0,0,25,25);
        ver.addView(message);
        ver.addView(Username);



    }

    public void getMessage(JSONObject jo){
        try {
            message.setText(jo.getString("MESSAGE"));
            Username.setText("From "+jo.getString("REQ_USERNAME"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
