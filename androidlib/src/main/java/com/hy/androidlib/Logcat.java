package com.hy.androidlib;

import android.util.Log;

/**
 * 日志打印。
 *
 * @author hy 2018/2/2
 */
public class Logcat {

    public static final int VERBOSE = 0;
    public static final int DEBUG = 1;
    public static final int INFO = 2;
    public static final int WARRING = 3;
    public static final int ERROR = 4;

    private static boolean isLog = BuildConfig.DEBUG;
    private static int level = ERROR;

    /**
     * 设置是否输出日志。
     *
     * @param log
     */
    public static void setIsLog(boolean log) {
        isLog = log;
    }

    /**
     * 设置日志等级。
     *
     * @param level {@link #VERBOSE} {@link #DEBUG} {@link #INFO} {@link #WARRING} {@link #ERROR}
     */
    public static void setLevel(int level) {
        Logcat.level = level;
    }

    public static void v(String tag, String message) {
        if (isLog && level >= VERBOSE) {
            Log.v(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (isLog && level >= DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (isLog && level >= INFO) {
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (isLog && level >= WARRING) {
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (isLog && level >= ERROR) {
            Log.e(tag, message);
        }
    }

}
