package com.hy.multiprocess.mainprocess;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hy.multiprocess.R;

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

    Application mApplication;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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

        button1.setOnClickListener(v -> startActivity(new Intent("com.hy.Mp")));
        button2.setOnClickListener(v -> startActivity(new Intent("com.hy.Bp1")));
        button3.setOnClickListener(v -> startActivity(new Intent("com.hy.Bp2")));

        mApplication = getApplication();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        text.append("onNewIntent\n");
    }
}
