package com.hy.androidlib.utils;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by chensenhua on 2017/12/2.
 * 内存泄漏检测管理类
 * 通过{@link #openLeakCheck(Application)}方法启动检测
 */

public class LeakCanaryManager {

    private static LeakCanaryManager mInstance;
    private RefWatcher mRefWatcher;

    private LeakCanaryManager() {

    }

    public static LeakCanaryManager getInstance() {
        if (mInstance == null) {
            mInstance = new LeakCanaryManager();
        }
        return mInstance;
    }


    /**
     * 启动内存检测
     * @param application
     */
    public void openLeakCheck(Application application) {
        if (mRefWatcher == null) {
            mRefWatcher = LeakCanary.install(application);
        }
    }

    /**
     * 监测指定对象
     * @param obj 实例对象
     */
    public void watch(Object obj) {
        if (mRefWatcher != null) {
            mRefWatcher.watch(obj);
        }
    }

}
