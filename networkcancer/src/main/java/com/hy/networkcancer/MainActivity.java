package com.hy.networkcancer;

import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hy.androidlib.network.WifiHelper;
import com.hy.androidlib.utils.ToastUtil;
import com.hy.androidlib.widget.ButtonsLayout;
import com.hy.networkcancer.shooter.Shooter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mLog)
    TextView mLog;
    @BindView(R.id.buttonsLayout)
    ButtonsLayout buttonsLayout;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    private Handler mMainHandler;

    private WifiHelper wifiHelper;
    private WifiCallback wifiCallback = new WifiCallback();

    private Shooter shooter;
    private Shooter.LogListener listener = (who, message) -> {
        mMainHandler.post(() -> mLog.append(who + ": " + message + '\n'));
        scrollView.fullScroll(View.FOCUS_DOWN);
    };
    private View dialogLayout;
    private EditText mEditPort;
    private EditText mEditPacketSize;
    private SeekBar mSendSpeed;
    private AlertDialog sendOptionDialog;
    private EditText mEditSockets;

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

        // 初始化。
        dialogLayout = LayoutInflater.from(this).inflate(R.layout.layout_udp_option, null, false);
        mEditPort = (EditText) dialogLayout.findViewById(R.id.mEditPortText);
        mEditPort.setText("9999");
        mEditPacketSize = (EditText) dialogLayout.findViewById(R.id.mEditPacketSize);
        mEditPacketSize.setText("1024");
        mEditSockets = (EditText) dialogLayout.findViewById(R.id.mEditSockets);
        mEditSockets.setText("10");
        mSendSpeed = (SeekBar) dialogLayout.findViewById(R.id.seekBar);
        sendOptionDialog = new AlertDialog.Builder(this)
                .setPositiveButton("确定", (dialog1, which) -> {
                    int sockets = Integer.parseInt(String.valueOf(mEditSockets.getText()));
                    int packetSize = Integer.parseInt(String.valueOf(mEditPacketSize.getText()));
                    int port = Integer.parseInt(String.valueOf(mEditPort.getText()));
                    int progress = mSendSpeed.getProgress();
                    int sleepTime = mSendSpeed.getMax() - progress;

                    if (shooter != null) {
                        shooter.stopShoot();
                    }
                    try {
                        shooter = new Shooter(sockets, packetSize, port, sleepTime);
                        shooter.setLogListener(listener);
                        shooter.startShoot();
                    } catch (Exception e) {
                        ToastUtil.showToastLong("参数非法." + e.getMessage());
                    }
                })
                .setNegativeButton("取消", (dialog12, which) -> {

                })
                .setView(dialogLayout)
                .setTitle("轰炸选项")
                .create();

        buttonsLayout.addButtons("udp广播轰炸", v -> {
            sendOptionDialog.show();
        });
        buttonsLayout.addButtons("停止", v -> {
            if (shooter != null) {
                shooter.stopShoot();
            }
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
