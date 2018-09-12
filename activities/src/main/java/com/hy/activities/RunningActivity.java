package com.hy.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

public class RunningActivity extends AbstractActivity {

    private boolean mHasExecOnCreate;

    ViewStub mViewStub;
    TextView mNormalTextView;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RunningActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_stub_layout);

        mTextView = findViewById(R.id.text);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);

        mTextView.setText(getName());
        mButton1.setOnClickListener(this::onClick1);
        mButton2.setOnClickListener(this::onClick2);
        mButton3.setOnClickListener(this::onClick3);

        mViewStub = findViewById(R.id.view_stub);
        mViewStub.inflate();

        mNormalTextView = findViewById(R.id.mNormalText);

        mHasExecOnCreate = true;

        Mode.getInstance().setRunningActivity(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
//        Looper.myQueue().addIdleHandler(() -> {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Log.i(getName() + " " + RunningActivity.this,
//                    Thread.currentThread().getName() + " idleHandler has been run.");
//            return false;
//        });
//
//        new Handler(Looper.getMainLooper()).postDelayed(() -> {
//            Log.i(getName() + " " + this, "20s runnable has been run.");
//        }, 20 * 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mHasExecOnCreate) {
            throw new RuntimeException("no on create");
        }
        mNormalTextView.setText(mNormalTextView.getText() + "1");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public String getName() {
        return "RunningActivity";
    }

    @Override
    public void onClick1(View v) {
        ShareActivity.startActivity(this);
    }

    @Override
    public void onClick2(View v) {

    }

    @Override
    public void onClick3(View v) {

    }

}
