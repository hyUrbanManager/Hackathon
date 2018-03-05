package com.hy.multiprocess.backgroundprocess1;

import android.app.Application;

import com.hy.androidlib.Logcat;

/**
 * Created by Administrator on 2018/3/4.
 *
 * @author hy 2018/3/4
 */
public class BP1Application extends Application {

    public static final String TAG = "BP1Application";

    @Override
    public void onCreate() {
        super.onCreate();
        Logcat.i(TAG, "onCreate");
    }
}
