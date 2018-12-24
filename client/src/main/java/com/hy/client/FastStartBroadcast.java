package com.hy.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * adb命令快速启动activity。
 * 没有工作，无法收到广播。why？
 */
public class FastStartBroadcast extends BroadcastReceiver {

    private static final String TAG = "@FastStart";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + intent);
        int code = intent.getIntExtra("code", 0);

        Class<?> clazz = null;
        switch (code) {
            case 1010:
                clazz = WebActivity.class;
                break;
            default:
        }

        if (clazz != null) {
            context.startActivity(new Intent(context, clazz));
        }
    }
}
