package com.hy.media;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hy.media.player.FFTest;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FFMpegInfoActivity extends AppCompatActivity {

    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.button5)
    Button button5;
    @BindView(R.id.line1)
    LinearLayout line1;
    @BindView(R.id.text)
    TextView text;

    public String[] paths = {
            "future h264 aac.mkv",
            "future h265 aac.mp4",
            "future mpeg4 mp3.avi",
            "future wmv2.wmv",
            "future.mp4",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ffmpeg_info);
        ButterKnife.bind(this);

        button1.setOnClickListener(v -> getInfo(0));
        button2.setOnClickListener(v -> getInfo(1));
        button3.setOnClickListener(v -> getInfo(2));
        button4.setOnClickListener(v -> getInfo(3));
        button5.setOnClickListener(v -> getInfo(4));
    }

    private void getInfo(int index) {
        String path = new File(Environment.getExternalStorageDirectory(),
                paths[index]).getAbsolutePath();
        String info = FFTest.getInfo(path);
        text.setText(info);
    }
}
