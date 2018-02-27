package com.hy.androidlib.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import com.jieli.component.Logcat;
import com.jieli.component.network.WifiHelper;

/**
 * Created by chensenhua on 2018/2/24.
 * 网络状态广播接收类
 */

public class NetworkStateBroadReceiver extends BroadcastReceiver {
    private String tag = getClass().getSimpleName();
    private WifiHelper mWifiHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mWifiHelper == null) {
            mWifiHelper = WifiHelper.getInstance(context.getApplicationContext());
        }
        if (context != null && intent != null) {
            String action = intent.getAction();
            if (TextUtils.isEmpty(action)) return;
            switch (action) {
                case WifiManager.NETWORK_STATE_CHANGED_ACTION:
                    NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                    WifiInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
                    if (networkInfo == null) {
                        Logcat.e(tag, "networkInfo is null");
                        mWifiHelper.notifyWifiState(WifiHelper.STATE_NETWORK_INFO_EMPTY);
                        return;
                    }
//                        Logcat.w(TAG, "networkInfo : " + networkInfo.toString());
                    boolean isWifiConnected = (networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED);
                    if (isWifiConnected) {
                        if (networkInfo.getType() != ConnectivityManager.TYPE_WIFI) {
                            Logcat.e(tag, "networkType is not TYPE_WIFI");
                            mWifiHelper.notifyWifiState(WifiHelper.STATE_NETWORK_TYPE_NOT_WIFI);
                            return;
                        }
                        if (wifiInfo == null || TextUtils.isEmpty(wifiInfo.getSSID())) {
                            Logcat.e(tag, "wifiInfo is  empty or ssid is null");
                            mWifiHelper.notifyWifiState(WifiHelper.STATE_WIFI_INFO_EMPTY);
                            return;
                        }
//                        Logcat.i(TAG, "wifiInfo : " + wifiInfo.toString());
                        mWifiHelper.notifyWifiConnect(wifiInfo);  //wifi连接成功
                    }
                    break;
                case WifiManager.SUPPLICANT_STATE_CHANGED_ACTION:
                    SupplicantState state = intent.getParcelableExtra(WifiManager.EXTRA_NEW_STATE);
                    int supplicantError = intent.getIntExtra(WifiManager.EXTRA_SUPPLICANT_ERROR, -1);
                    Logcat.i(tag, "supplicantError=" + supplicantError + ", state=" + state);
                    if (SupplicantState.DISCONNECTED.equals(state) && supplicantError == WifiManager.ERROR_AUTHENTICATING) {
                        mWifiHelper.notifyWifiState(WifiHelper.STATE_WIFI_PWD_NOT_MATCH); //wifi密码错误
                    }
                    break;
                case ConnectivityManager.CONNECTIVITY_ACTION:
                    if (!mWifiHelper.isWifiOpen()) {
                        int wifiState;
                        if (WifiHelper.isNetWorkConnectedType(context.getApplicationContext(), ConnectivityManager.TYPE_MOBILE)) {
                            wifiState = WifiHelper.STATE_NETWORK_TYPE_NOT_WIFI; //移动网络
                        } else {
                            wifiState = WifiHelper.STATE_NETWORK_NOT_OPEN;   //网络未打开
                        }
                        mWifiHelper.notifyWifiState(wifiState);
                    }
                    break;
            }
        }
    }


}
