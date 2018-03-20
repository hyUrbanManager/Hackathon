package com.hy.media.player;

import android.view.SurfaceView;

/**
 * 调用ffmpeg的封装解码。
 *
 * @author hy 2018/3/20
 */
public class FFPlayer {

    // 加载ffmpeg动态库，jni库。
    static {
        System.loadLibrary("avcodec");
        System.loadLibrary("avdevice");
        System.loadLibrary("avfilter");
        System.loadLibrary("avformat");
        System.loadLibrary("avutil");
        System.loadLibrary("swresample");
        System.loadLibrary("swscale");

        System.loadLibrary("video");
    }

    public static native void play(String path, SurfaceView surfaceView);

    public static native void pause();
}
