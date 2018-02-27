package com.hy.androidlib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Toast 辅助类。
 *
 * @author hy 2017/12/5
 */
public class ToastUtil {

    private static Toast mToast;

    // 弱引用Context。
    private static WeakReference<Context> contextWeakReference;

    /**
     * 初始化Toast管理器。
     *
     * @param applicationContext 系统的Context。
     */
    public static void init(Context applicationContext) {
        ToastUtil.contextWeakReference = new WeakReference<>(applicationContext);
    }


    /**
     * 显示内容。
     *
     * @param msg      内容
     * @param duration 显示间隔
     */
    @SuppressLint("ShowToast")
    public static void showToast(String msg, int duration) {
        if (contextWeakReference == null) {
            throw new RuntimeException("u have not init toast utils");
        }
        if (contextWeakReference.get() == null) {
            return;
        }
        if (!TextUtils.isEmpty(msg) && duration >= 0) {
            if (mToast == null) {
                mToast = Toast.makeText(contextWeakReference.get(), msg, duration);
            } else {
                mToast.setText(msg);
                mToast.setDuration(duration);
            }
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
        }
    }

    public static void showToastShort(String info) {
        showToast(info, Toast.LENGTH_SHORT);
    }

    public static void showToastShort(int info) {
        if (contextWeakReference == null) {
            throw new RuntimeException("u have not init toast utils");
        }
        if (contextWeakReference.get() == null) {
            return;
        }
        showToastShort(contextWeakReference.get().getResources().getString(info));
    }

    public static void showToastLong(String msg) {
        showToast(msg, Toast.LENGTH_LONG);
    }

    public static void showToastLong(int msg) {
        if (contextWeakReference == null) {
            throw new RuntimeException("u have not init toast utils");
        }
        if (contextWeakReference.get() == null) {
            return;
        }
        showToastLong(contextWeakReference.get().getResources().getString(msg));
    }
}
