package com.hy.androidlib.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Telephony;

import com.hy.androidlib.Logcat;


/**
 * Created by chensenhua on 2018/1/17.
 */

public class SmsBroadcastReceiver extends BroadcastReceiver {
    private String tag = getClass().getSimpleName();

    private Context context;

    public SmsBroadcastReceiver(Context context) {
        this.context = context;
        IntentFilter filter = new IntentFilter();
        filter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        context.registerReceiver(this, filter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Logcat.e(tag, "----------------------" + intent.getAction().toString() + "----------------");
    }

    public void release() {
        context.unregisterReceiver(this);
        context = null;
    }
}
