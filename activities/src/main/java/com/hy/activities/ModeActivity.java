package com.hy.activities;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class ModeActivity extends AbstractActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ModeActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public String getName() {
        return "ModeActivity";
    }

    @Override
    public void onClick1(View v) {
        RunningActivity.startActivity(this);
    }

    @Override
    public void onClick2(View v) {

    }

    @Override
    public void onClick3(View v) {

    }

}
