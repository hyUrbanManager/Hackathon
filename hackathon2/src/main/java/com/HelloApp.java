package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class HelloApp {

    public static void main(String[] ags) throws Exception {
        // 在屏幕上显示hello world
        // textview
        screenprint("hello world", 0, 0);

        // 在屏幕上显示一个图片
        // imageView
        File file = new File("/sdcard/test.png");
        int width = 1920;
        int height = 1080;
        byte[] bytes = new byte[width * height * 4];
        InputStream is = new FileInputStream(file);
        is.read(bytes);
        pxielFill(bytes, 0, 0);

    }

    // 处理屏幕点击事件
    // button
    public void onScreenTouchInterrupt(int x, int y) {
        if (x > 150 && x <= 300 && y > 450 && y <= 600) {
            screenprint("onclick", 0, 0);
        }
    }

    private static void screenprint(String onclick, int i, int i1) {
    }

    private static void pxielFill(byte[] bytes, int i, int i1) {
    }
}
