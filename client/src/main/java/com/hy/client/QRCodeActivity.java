package com.hy.client;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hy.androidlib.utils.ToastUtil;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QRCodeActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.image)
    ImageView image;

    public static final String website = "http://139.199.170.98";
    public static final String longText = "giteveryday(7) Manual Page\n" +
            "NAME\n" +
            "giteveryday - A useful minimum set of commands for Everyday Git\n" +
            "\n" +
            "SYNOPSIS\n" +
            "Everyday Git With 20 Commands Or So\n" +
            "\n" +
            "DESCRIPTION\n" +
            "Git users can broadly be grouped into four categories for the purposes of describing here a small set of useful command for everyday Git.\n" +
            "\n" +
            "Individual Developer (Standalone) commands are essential for anybody who makes a commit, even for somebody who works alone.\n" +
            "\n" +
            "If you work with other people, you will need commands listed in the Individual Developer (Participant) section as well.\n" +
            "\n" +
            "People who play the Integrator role need to learn some more commands in addition to the above.\n" +
            "\n" +
            "Repository Administration commands are for system administrators who are responsible for the care and feeding of Git repositories.\n" +
            "\n" +
            "Individual Developer (Standalone)\n" +
            "A standalone individual developer does not exchange patches with other people, and works alone in a single repository, using the following commands.\n" +
            "\n" +
            "git-init(1) to create a new repository.\n" +
            "\n" +
            "git-log(1) to see what happened.\n" +
            "\n" +
            "git-checkout(1) and git-branch(1) to switch branches.\n" +
            "\n" +
            "git-add(1) to manage the index file.\n" +
            "\n" +
            "git-diff(1) and git-status(1) to see what you are in the middle of doing.\n" +
            "\n" +
            "git-commit(1) to advance the current branch.\n" +
            "\n" +
            "git-reset(1) and git-checkout(1) (with pathname parameters) to undo changes.\n" +
            "\n" +
            "git-merge(1) to merge between local branches.\n" +
            "\n" +
            "git-rebase(1) to maintain topic branches.\n" +
            "\n" +
            "git-tag(1) to mark a known point.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);

        button.setOnClickListener(v -> {
            long start = SystemClock.currentThreadTimeMillis();
            Bitmap bitmap = createQRCode(website, 400, 400);
            if (bitmap != null) {
                image.setImageBitmap(bitmap);
                long duration = SystemClock.currentThreadTimeMillis() - start;
                ToastUtil.showToastLong("生成二维码成功，耗时 " + duration + " ms");
            }
        });

        button2.setOnClickListener(v -> {
            long start = SystemClock.currentThreadTimeMillis();
            Bitmap bitmap = createQRCode(longText, 1000, 1000);
            if (bitmap != null) {
                image.setImageBitmap(bitmap);
                long duration = SystemClock.currentThreadTimeMillis() - start;
                ToastUtil.showToastLong("生成二维码成功，耗时 " + duration + " ms");
            }
        });
    }

    /**
     * 生成二维码。
     *
     * @param s
     * @return
     */
    public Bitmap createQRCode(String s, int w, int h) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(s, BarcodeFormat.QR_CODE, w, h);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            return barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            ToastUtil.showToastLong("失败。" + e.getMessage());
            return null;
        }
    }
}
