package com.hy.media;

import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件复制帮助类。
 *
 * @author hy 2018/3/22
 */
public class FileUtil {

    public final static String TAG = "@FileUtil";

    /**
     * 流拷贝到指定文件。
     *
     * @param is
     * @param file
     */
    public static void isToFile(InputStream is, File file) {
        if (file.exists()) {
            Log.i(TAG, "file: " + file.getName() + " is exist.");
            return;
        }
        byte[] bytes = new byte[1024 * 10];
        int len;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            while ((len = is.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(is);
            close(fos);
        }
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
