package com.hy.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkhttpActivity extends AppCompatActivity {

    private static final String TAG = "@OkhttpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        findViewById(R.id.button).setOnClickListener(v -> {
            doReuqest();
        });
    }

    public void doReuqest() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .followRedirects(false)
                .build();

        FormBody formBody = new FormBody.Builder()
                .add("luci_username", "root")
                .add("luci_password", "maxhub1379")
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.153.1/cgi-bin/luci/")
                .post(formBody)
                .build();

        Log.d(TAG, "start execute");

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFail", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    Log.i(TAG, "code: " + response.code());
                    Log.i(TAG, "headers: " + response.headers());
                    Log.i(TAG, "sysauth: " + response.header("Set-Cookie").split(";")[0].split("=")[1]);
                } catch (Exception e) {
                    Log.e(TAG, "onResponse", e);
                }
            }
        });


    }
}
