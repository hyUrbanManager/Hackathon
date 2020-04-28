package com.hy.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hy.client.utils.MyHttpLoggingInterceptor;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
                    String auth = response.header("Set-Cookie").split(";")[0];
                    Log.i(TAG, "sysauth: " + auth);

                    doRequest2(auth);

                } catch (Exception e) {
                    Log.e(TAG, "onResponse", e);
                }
            }
        });
    }

    private void doRequest2(String auth) {
        MyHttpLoggingInterceptor logging = new MyHttpLoggingInterceptor();
        logging.setLevel(MyHttpLoggingInterceptor.Level.BODY);
        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        // 文件传输
        String path = "/storage/6DC9-F687/sysupgrade9.bin";
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "sysupgrade9.bin",
                        RequestBody.create(MediaType.parse("multipart/form-data"), new File(path)))
                .build();

        Request requestFile = new Request.Builder()
                .addHeader("Cookie", auth)
                .addHeader("Host", "192.168.153.1")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:67.0) Gecko/20100101 Firefox/67.0")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("Cache-Control", "max-age=0")
                .url("http://192.168.153.1/cgi-bin/luci/admin/system/flashops")
                .post(requestBody)
                .build();
        Log.e(TAG, "requestFile" + requestFile.headers());

        client.newCall(requestFile).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.i(TAG, String.valueOf(response.body()));
//                Log.i(TAG, String.valueOf(response.code()));
//                Log.i(TAG, String.valueOf(response.headers()));
//                Log.i(TAG, String.valueOf(response.message()));
//                Log.i(TAG, String.valueOf(response.request()));
            }
        });
    }

    private void doRequest3(String auth) {
        // 升级
        OkHttpClient clientUpgrade = new OkHttpClient().newBuilder().build();
        FormBody formBodUpgrade = new FormBody.Builder()
                .add("step", "2")
                .add("keep", "0")
                .build();

        Request requestUpgrade = new Request.Builder()
                .addHeader("Cookie", auth)
                .addHeader("Host", "192.168.153.1")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:67.0) Gecko/20100101 Firefox/67.0")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("Cache-Control", "max-age=0")
                .url("http://192.168.153.1/cgi-bin/luci/admin/system/flashops")
                .post(formBodUpgrade)
                .build();
        Log.e(TAG, "requestUpgrade" + requestUpgrade.headers());

        clientUpgrade.newCall(requestUpgrade).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, String.valueOf(response.body()));
                Log.i(TAG, String.valueOf(response.code()));
                Log.i(TAG, String.valueOf(response.headers()));
                Log.i(TAG, String.valueOf(response.message()));
                Log.i(TAG, String.valueOf(response.request()));
            }
        });

    }
}
