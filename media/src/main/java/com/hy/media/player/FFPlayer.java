package com.hy.media.player;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.view.Surface;

import com.hy.media.FileUtil;
import com.hy.media.MediaApplication;

import java.io.File;
import java.io.IOException;

/**
 * 调用ffmpeg的封装解码。
 *
 * @author hy 2018/3/20
 */
@SuppressLint("UnsafeDynamicallyLoadedCode")
public class FFPlayer {

    // 加载ffmpeg动态库，jni库。
    static {
//        System.loadLibrary("avutil");
//        System.loadLibrary("swresample");
//        System.loadLibrary("avcodec");
//        System.loadLibrary("avformat");
//        System.loadLibrary("swscale");
//        System.loadLibrary("avfilter");
//        System.loadLibrary("avdevice");

        File filesDir = MediaApplication.getInstance().getFilesDir();
        AssetManager as = MediaApplication.getInstance().getAssets();
        try {
            FileUtil.isToFile(as.open("lib/armeabi/libavutil.so.55"), new File(filesDir, "libavutil.so.55"));
            FileUtil.isToFile(as.open("lib/armeabi/libswresample.so.2"), new File(filesDir, "libswresample.so.2"));
            FileUtil.isToFile(as.open("lib/armeabi/libavcodec.so.57"), new File(filesDir, "libavcodec.so.57"));
            FileUtil.isToFile(as.open("lib/armeabi/libavformat.so.57"), new File(filesDir, "libavformat.so.57"));
            FileUtil.isToFile(as.open("lib/armeabi/libswscale.so.4"), new File(filesDir, "libswscale.so.4"));
            FileUtil.isToFile(as.open("lib/armeabi/libavfilter.so.6"), new File(filesDir, "libavfilter.so.6"));
            FileUtil.isToFile(as.open("lib/armeabi/libavdevice.so.57"), new File(filesDir, "libavdevice.so.57"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String path = filesDir.getAbsolutePath();
        System.load(path + "/libavutil.so.55");
        System.load(path + "/libswresample.so.2");
        System.load(path + "/libavcodec.so.57");
        System.load(path + "/libavformat.so.57");
        System.load(path + "/libswscale.so.4");
        System.load(path + "/libavfilter.so.6");
        System.load(path + "/libavdevice.so.57");

        System.loadLibrary("video");

        // 初始化。
        init();
    }

    private static native int init();

    public static native void play(String path, Surface surface);

    public static native void pause();
}
