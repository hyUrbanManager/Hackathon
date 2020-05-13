package com.hy.client;

import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SurfaceViewActivity extends AppCompatActivity {

    private static final String TAG = "@SurfaceViewActivity";

    @BindView(R.id.surface_view)
    SurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_view);
        ButterKnife.bind(this);

        Log.d(TAG, "onCreate");

        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d(TAG, "surfaceCreated");
                Canvas canvas = holder.lockCanvas();
                canvas.drawARGB(255, 243, 194, 194);
                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.d(TAG, "surfaceChanged: (" + width + ", " + height + ")");
                Canvas canvas = holder.lockCanvas();
                canvas.drawARGB(255, 243, 194, 194);
                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d(TAG, "surfaceDestroyed");
            }
        });

//        mSurfaceView.setZOrderOnTop(true);
        mSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
//        mSurfaceView.getHolder().setFormat(PixelFormat.RGB_565);
    }

}
