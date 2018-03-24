package com.hy.media;

import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NativeDrawActivity extends AppCompatActivity {

    static {
        try {
            Class.forName("com.hy.media.player.FFPlayer");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.line1)
    LinearLayout line1;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;

    private Random random = new Random();
    private boolean isStart = false;

    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_draw);
        ButterKnife.bind(this);

        button1.setOnClickListener(v -> {
            native_draw1(surfaceView.getHolder().getSurface());
        });

        button2.setOnClickListener(v -> {
            native_draw2(surfaceView.getHolder().getSurface());
        });

        button3.setOnClickListener(v -> {
            SurfaceHolder holder = surfaceView.getHolder();
            Surface surface = holder.getSurface();

            // 获取画布。
            Canvas canvas = holder.lockCanvas();

            canvas.drawARGB(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));

            holder.unlockCanvasAndPost(canvas);
        });

        button4.setOnClickListener(v -> {
            if (isStart) {
                return;
            }
            isStart = true;

            new Thread(() -> {
                int r = 255, g = 0, b = 0;
                int dir = 0;
                while (isStart) {
                    if (dir == 0) {
                        r--;
                    } else {
                        r++;
                    }
                    if (r >= 255) {
                        dir = 0;
                        r = 255;
                    }
                    if (r <= 0) {
                        dir = 1;
                        r = 0;
                    }
                    g = 255 - r;

//                    b += 2;
//                    if (b >= 255) {
//                        b = 0;
//                    }

                    setColor(r, g, b);

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isStart = false;
    }

    private void setColor(int r, int g, int b) {
        SurfaceHolder holder = surfaceView.getHolder();
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            canvas.drawARGB(255, r, g, b);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private native void native_draw1(Surface surface);

    private native void native_draw2(Surface surface);
}
