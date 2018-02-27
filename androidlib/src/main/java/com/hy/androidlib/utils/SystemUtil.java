package com.hy.androidlib.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 系统相关工具类工具类
 *
 * @author zqjasonZhong
 *         date : 2017/12/26
 */
public class SystemUtil {

    private static final String TAG = "SystemUtil";

    /**
     * 设置沉浸式状态栏。
     *
     * @param mWindow      当前Activity的Window对象。
     * @param isLightState 设置状态栏是否为浅色，状态栏浅色则字体为深黑色，状态栏深色则字体白色，
     */
    public static void setImmersiveStateBar(Window mWindow, boolean isLightState) {
        if (mWindow != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

            mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            View view = mWindow.getDecorView();
            if (view != null) {
                view.setSystemUiVisibility(option);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                mWindow.setStatusBarColor(Color.TRANSPARENT);
            }
            if (isLightState) {
                String phoneManufacturer = Build.BRAND;
                if (!TextUtils.isEmpty(phoneManufacturer)) {
//                    Logcat.i(TAG, "phoneManufacturer : " + phoneManufacturer);
                    if (phoneManufacturer.contains("Meizu") || phoneManufacturer.contains("meizu")) {
                        if (FlymeSetStatusBarLightMode(mWindow, true)) {
                            return;
                        }
                    } else if (phoneManufacturer.contains("Xiaomi") || phoneManufacturer.contains("xiaomi")) {
                        if (MIUISetStatusBarLightMode(mWindow, true)) {
                            return;
                        }
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            }
        }
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 需要MIUIV6以上
     *
     * @param window
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (dark) {
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 从raw文件夹获取资源
     *
     * @param context 上下文
     * @param rawId   raw资源ID
     */
    public static byte[] getFromRaw(Context context, int rawId) {
        byte[] result = null;
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().openRawResource(rawId);
            byte[] temp = new byte[1024 * 1024];
            byte[] buffer = new byte[1024];
            int size = 0;
            int fileSize = size;
            while ((size = inputStream.read(buffer, 0, 1024)) >= 0) {
                System.arraycopy(buffer, 0, temp, fileSize, 1024);
                fileSize += size;
            }
            if (fileSize > 0) {
                result = new byte[fileSize];
                System.arraycopy(temp, 0, result, 0, fileSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 判断APP是否在后台
     *
     * @param context 上下文
     * @return true : 后台 false : 前景
     */
    public static boolean isAppInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return false;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }

    /**
     * 读取在array.xml定义的图片资源列表。
     *
     * @param context     上下文
     * @param drawableRes 在array.xml定义的图片资源列表的id
     * @return 图片资源的id数组
     */
    public static int[] getDrawableResIdFromArray(Context context, int drawableRes) {
        TypedArray ar = context.getResources().obtainTypedArray(drawableRes);
        int len = ar.length();
        int[] resIds = new int[len];
        for (int i = 0; i < len; i++) {
            resIds[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();
        return resIds;
    }

    /**
     * 获取版本名
     */
    public static String getVersioName(Context context) {
        PackageManager pm = context.getPackageManager();
   /*     appName = "MengHuanHuanLeCheng";
            appVersion = pm.getPackageInfo(getPackageName(), 0).versionCode;*/
        try {
            return pm.getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "default_version_name";
    }

    /**
     * 获取版本号
     */
    public static int getVersion(Context context) {
        PackageManager pm = context.getPackageManager();
   /*     appName = "MengHuanHuanLeCheng";
            appVersion = pm.getPackageInfo(getPackageName(), 0).versionCode;*/
        try {
            return pm.getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 获取App名称
     */
    public static String getAppName(Context context) {
        PackageManager pm = context.getPackageManager();
        CharSequence name = pm.getApplicationLabel(context.getApplicationInfo());
        if (TextUtils.isEmpty(name)) {
            throw new RuntimeException("app名称读取失败");
        } else {
            return name.toString();
        }
    }


}
