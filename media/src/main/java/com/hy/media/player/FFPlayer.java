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

    public static native void play(String path, Surface surface);

    public static native void pause();
}
