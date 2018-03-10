package com.hy.client;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hy.androidlib.utils.ToastUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadJPEGActivity extends AppCompatActivity {

    @BindView(R.id.fileInfo)
    TextView fileInfo;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.line1)
    LinearLayout line1;
    @BindView(R.id.decodeInfo)
    TextView decodeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_jpeg);
        ButterKnife.bind(this);
        initView();
    }

    @SuppressLint("SetTextI18n")
    public void initView() {
        File f = getCameraFile();
        if (f != null) {
            fileInfo.setText("file: " + f.getName() + " size: " + f.length() / 1024 + " K");
        }
        button1.setText("原始加载");
        button1.setOnClickListener(v -> decodeJpg1());
        button2.setText("缩小加载");
        button2.setOnClickListener(v -> decodeJpg2());
        button3.setText("原始加载");
        button3.setOnClickListener(v -> decodeJpg1());
        button4.setText("原始加载");
        button4.setOnClickListener(v -> decodeJpg1());
    }

    public File getCameraFile() {
        File dir = new File(Environment.getExternalStorageDirectory(), "DCIM");
        if (dir.exists()) {
            File[] fs = dir.listFiles();
            File f = fs[fs.length - 1];
            ToastUtil.showToastShort(f.getName());
            return f;
        }
        ToastUtil.showToastShort("cannot find");
        return null;
    }

    @SuppressLint("SetTextI18n")
    public void decodeJpg1() {
        File file = getCameraFile();
        if (file == null) {
            return;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        image.setImageBitmap(bitmap);
        decodeInfo.setText("bytes: " + bitmap.getByteCount() + " size: " + bitmap.getByteCount() / 1024 + " K");
    }

    @SuppressLint("SetTextI18n")
    public void decodeJpg2() {
        File file = getCameraFile();
        if (file == null) {
            return;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap b = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

        int imgW = options.outWidth;
        int imgH = options.outHeight;
        int scale = Math.max(imgH / image.getHeight(), imgW / image.getWidth());
        scale *= 4;

        options.inJustDecodeBounds = false;
        options.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

        image.setImageBitmap(bitmap);
        decodeInfo.setText("bytes: " + bitmap.getByteCount() + " size: " + bitmap.getByteCount() / 1024 + " K");
    }
}


















