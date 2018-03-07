package com.hy.multiprocess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hy.androidlib.Logcat;

/**
 * Created by Administrator on 2018/3/7.
 *
 * @author hy 2018/3/7
 */
public class MyBroadCastReceiver extends BroadcastReceiver {
    public static final String TAG = "MyBroadCastReceiver";

    public static final String ACTION = "hy.startCount";

    public int times = 0;

    public MyBroadCastReceiver() {
        times++;
        Logcat.i(TAG, "created, times: " + times);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Logcat.i(TAG, "action: " + action + ", times: " + times);
    }
}
