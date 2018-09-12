package com.hy.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by huangye on 2018/9/8.
 *
 * @author hy 2018/9/8
 */
public abstract class AbstractActivity extends Activity {

    TextView mTextView;

    Button mButton1;
    Button mButton2;
    Button mButton3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_layout);
        mTextView = findViewById(R.id.text);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);

        mTextView.setText(getName());
        mButton1.setOnClickListener(this::onClick1);
        mButton2.setOnClickListener(this::onClick2);
        mButton3.setOnClickListener(this::onClick3);

        Log.d(getName() + " " + this, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(getName() + " " + this, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(getName() + " " + this, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(getName() + " " + this, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(getName() + " " + this, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(getName() + " " + this, "onDestroy");
    }

    public abstract String getName();

    public abstract void onClick1(View v);
    public abstract void onClick2(View v);
    public abstract void onClick3(View v);
}
