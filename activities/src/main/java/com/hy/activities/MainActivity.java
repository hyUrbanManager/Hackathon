package com.hy.activities;

import android.content.Intent;
import android.view.View;

public class MainActivity extends AbstractActivity {

    @Override
    public String getName() {
        return "MainActivity";
    }

    @Override
    public void onClick1(View v) {
        ModeActivity.startActivity(this);
    }

    @Override
    public void onClick2(View v) {
    }

    @Override
    public void onClick3(View v) {
        Intent intent = new Intent(this, ShareActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
