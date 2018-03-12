package com.hy.multiprocess.mainprocess;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hy.androidlib.utils.ToastUtil;
import com.hy.multiprocess.IToken;
import com.hy.multiprocess.MyBroadCastReceiver;
import com.hy.multiprocess.R;
import com.hy.multiprocess.backgroundprocess1.TokenService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MPActivity extends AppCompatActivity {

    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.line1)
    LinearLayout line1;
    @BindView(R.id.button4)
    Button button4;

    private IToken service;
    private ServiceConnection connection;

    private Application mApplication;
    private BroadcastReceiver receiver;
    private Handler mMainHandler;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMainHandler = new Handler(Looper.getMainLooper());

        setTitle(getClass().getSimpleName());

        int pid = Process.myPid();

        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            text.setText("pid is " + pid);
            return;
        }

        StringBuilder sb = new StringBuilder();
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo rai : list) {
            sb.append(rai.processName).append(":").append(rai.pid).append("\n");
        }
        sb.append("this activity process: ").append(pid).append("\n");

        text.setText(sb.toString());

        button1.setOnClickListener(v -> {
            startActivity(new Intent("com.hy.Mp"));
//            sendBroadcast(new Intent(MyBroadCastReceiver.ACTION));
        });
        button2.setOnClickListener(v -> startActivity(new Intent("com.hy.Bp1")));
        button3.setOnClickListener(v -> startActivity(new Intent("com.hy.Bp2")));

        mApplication = getApplication();

        receiver = new MyBroadCastReceiver();

        // 界面启动完成后启动Service.
        Intent intent = new Intent(this, TokenService.class);
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                if (service == null) {
                    ToastUtil.showToastShort("onBind service is null");
                    return;
                }
                // as interface.
                MPActivity.this.service = IToken.Stub.asInterface(service);
                ToastUtil.showToastShort("onBind " + name.getClassName());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                MPActivity.this.service = null;
                ToastUtil.showToastShort("onDisConnected" + name.getClassName());
            }
        };
        line1.post(() -> bindService(intent, connection, BIND_AUTO_CREATE));

        button4.setOnClickListener(v -> {
            try {
                String token = service.getToken();
                ToastUtil.showToastLong("token: " + token);
            } catch (Exception e) {
                ToastUtil.showToastLong("get token fail. " + e.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyBroadCastReceiver.ACTION);
//        动态注册时，只有单实例。
//        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        text.append("onNewIntent\n");
    }
}
