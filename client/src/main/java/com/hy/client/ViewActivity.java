package com.hy.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scroll);
    }

    public static String measureModeToString(int mode) {
        if (mode == View.MeasureSpec.AT_MOST) {
            return "AT_MOST";
        } else if (mode == View.MeasureSpec.EXACTLY) {
            return "EXACTLY";
        } else if (mode == View.MeasureSpec.UNSPECIFIED) {
            return "UNSPECIFIED";
        } else {
            return "null mode";
        }
    }
}
