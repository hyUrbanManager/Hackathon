package com.hy.androidlib.media.proxy;

import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 数据到文件处理者，不对外开放。
 *
 * @author hy 2018/2/5
 */
class FileHandler implements DataHandler {

    String path;

    RandomAccessFile raf;

    public FileHandler(String path) {
        this.path = path;
    }

    @Override
    public void start() {
        try {
            raf = new RandomAccessFile(path, "rws");
        } catch (FileNotFoundException e) {
            Log.e(MediaPlayerProxy.TAG + " FileHandler", "", e);
        }
    }

    @Override
    public void write(byte[] bytes, int start, int len) {
        try {
            raf.write(bytes, start, len);
        } catch (Exception e) {
            Log.e(MediaPlayerProxy.TAG + " FileHandler", "write", e);
        }
    }

    @Override
    public void end() {
        if (raf != null) {
            try {
                raf.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 文件写入跳转到指定位置。
     */
    public void accessToPosition(long position) {
        try {
            raf.seek(position);
        } catch (Exception e) {
            Log.e(MediaPlayerProxy.TAG + " FileHandler", "raf seek fail.", e);
        }
    }
}
