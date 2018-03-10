package com.hy.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hy.androidlib.Logcat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "@MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentResolver resolver = getContentResolver();
        String type = resolver.getType(Uri.parse("content://com.hy.provider.AccountProvider"));
        Logcat.d(TAG, "type: " + type);


    }
}
