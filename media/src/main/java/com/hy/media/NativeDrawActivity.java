package com.hy.media;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NativeDrawActivity extends AppCompatActivity {

    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.line1)
    LinearLayout line1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_draw);
        ButterKnife.bind(this);

    }

    private native void native_draw1(Surface surface);

    private native void native_draw2(Surface surface);
}
