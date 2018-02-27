package com.hy.networkcancer;

import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridLayout;
import android.widget.TextView;

import com.hy.androidlib.network.WifiHelper;
import com.hy.androidlib.utils.ToastUtil;
import com.hy.androidlib.widget.ButtonsLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mLog)
    TextView mLog;
    @BindView(R.id.buttonsLayout)
    ButtonsLayout buttonsLayout;

    private Handler mMainHandler;

    private WifiHelper wifiHelper;
    private WifiCallback wifiCallback = new WifiCallback();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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

        buttonsLayout.addButtons("udp广播轰炸", v -> {

        });
        buttonsLayout.addButtons("udp广播轰炸", v -> {

        });
        buttonsLayout.addButtons("udp广播轰炸", v -> {

        });
        buttonsLayout.addButtons("udp广播轰炸", v -> {

        });
        buttonsLayout.addButtons("udp广播轰炸", v -> {

        });
        buttonsLayout.addButtons("udp广播轰炸", v -> {

        });

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
