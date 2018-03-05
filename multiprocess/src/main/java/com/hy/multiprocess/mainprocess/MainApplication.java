package com.hy.multiprocess.mainprocess;

import android.annotation.SuppressLint;
import android.app.Application;

import com.hy.androidlib.Logcat;
import com.hy.androidlib.utils.FileUtil;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2018/3/4.
 *
 * @author hy 2018/3/4
 */
public class MainApplication extends Application {

    public static final String TAG = "MainApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Logcat.i(TAG, "onCreate");

        try {
            Field field = this.getClass().getField("mLoadedApk");
            Object apk = field.get(this);

            @SuppressLint("PrivateApi")
            Class loadedApkClass = Class.forName("android.app.LoadedApk");
            Field[] fields = loadedApkClass.getDeclaredFields();
            Object[] objs = new Object[fields.length];
            int index = 0;
            for (Field f : fields) {
                objs[index++] = f.get(apk);
            }

            for (Object o : objs) {
                Logcat.i(TAG, o.getClass().getSimpleName() + ": " + o.toString());
            }

        } catch (Exception e) {
            Logcat.e(TAG, "load LoadedApk class fail. " + e.getMessage());
        }

    }
}
