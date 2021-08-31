package com.example.surrogateshopper;

import android.app.Activity;
import android.content.ContentValues;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.StringReader;
import java.text.Normalizer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PhpReq {
    String Url;
    PhpReq(String x){
        Url=x;
    }
    public void DoReq(final Activity a, ContentValues params, final Processor pro){
        OkHttpClient client = new OkHttpClient();

        FormBody.Builder builder=new FormBody.Builder();
        for (String key:params.keySet()){
            builder.add(key,params.getAsString(key));
        }
        Request request=new Request.Builder()
                .url(Url)
                .post(builder.build())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(!response.isSuccessful()){
                    throw new IOException("unexpected code"+response);
                }
                else{
                    final String responsedata=response.body().string();
                    a.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pro.converter(responsedata);
                        }
                    });
                }
            }
        });

    }
}
