package com.hy.networkcancer;

import android.net.wifi.WifiInfo;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hy.androidlib.network.WifiHelper;
import com.hy.androidlib.utils.ToastUtil;

public class MainActivity extends AppCompatActivity {

    private Handler mMainHandler;

    private WifiHelper wifiHelper;
    private WifiCallback wifiCallback = new WifiCallback();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainHandler = new Handler(Looper.getMainLooper());

        // 注册wifi。
        WifiHelper.NetState state = WifiHelper.getConnectedType(getApplicationContext());
        if (state == WifiHelper.NetState.NET_2G || state == WifiHelper.NetState.NET_3G
                || state == WifiHelper.NetState.NET_4G) {
            ToastUtil.showToastLong("你正在使用流量，即将退出程序。");
            mMainHandler.postDelayed(this::finish, 2000);
        } else if (state == WifiHelper.NetState.NET_NO || state == WifiHelper.NetState.NET_UNKNOWN) {
            ToastUtil.showToastLong("你还未连接到网络。");
        }

        wifiHelper = WifiHelper.getInstance();
        wifiHelper.registerBroadCastReceiver(getApplicationContext());
        wifiHelper.registerOnWifiCallback(wifiCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wifiHelper.unregisterBroadcastReceiver(getApplicationContext());
        wifiHelper.registerOnWifiCallback(wifiCallback);
    }

    public class WifiCallback implements WifiHelper.OnWifiCallBack {

        @Override
        public void onConnected(WifiInfo info) {
            ToastUtil.showToastLong("connected: " + info.getSSID());
        }

        @Override
        public void onState(int state) {
            ToastUtil.showToastLong("wifi state: " + state);
        }
    }

}
