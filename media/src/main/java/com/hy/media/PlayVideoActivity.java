package com.hy.media;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hy.media.player.FFPlayer;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayVideoActivity extends AppCompatActivity {

    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.line1)
    LinearLayout line1;
    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;

    // 视频路径。
    private String videoPath;

    private FFPlayer ffPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        ButterKnife.bind(this);

        // 设置像素格式。
        surfaceView.getHolder().setFormat(PixelFormat.RGBA_8888);

        videoPath = new File(Environment.getExternalStorageDirectory(), "130.mp4").getAbsolutePath();

        button1.setOnClickListener(v -> {
            Surface surface = surfaceView.getHolder().getSurface();
            FFPlayer.play(videoPath, surface);
        });
        button2.setOnClickListener(v -> {
            FFPlayer.pause();
        });
    }

}
