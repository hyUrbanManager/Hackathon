package com.hy.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.koushikdutta.async.AsyncServerSocket;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HttpServerActivity extends AppCompatActivity {

    private static final String TAG = "@HttpServerActivity";

    @BindView(R.id.text)
    TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_server);
        ButterKnife.bind(this);

        setTitle("http server");

        AsyncHttpServer server = new AsyncHttpServer();


        server.get("/download/file1", (request, response) -> {
//            response.send("Hello!!!");
            Log.d(TAG, "repsonse");
            response.sendFile(new File("/sdcard/meinv.mp4"));
        });

        // listen on port 5000
        AsyncServerSocket socket = server.listen(5000);

        mText.setText("http://{ip}:5000/download/file1");
    }
}
