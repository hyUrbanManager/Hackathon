package com.hy.activities;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class ShareActivity extends AbstractActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ShareActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Mode.getInstance().mRunningActivity != null) {
//            Mode.getInstance().mRunningActivity.finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public String getName() {
        return "ShareActivity";
    }

    @Override
    public void onClick1(View v) {

    }

    @Override
    public void onClick2(View v) {

    }

    @Override
    public void onClick3(View v) {

    }

}
