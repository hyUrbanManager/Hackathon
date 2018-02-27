package com.hy.androidlib;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import java.util.Stack;

/**
 * Created by chensenhua on 2018/2/23.
 */

public class ActivityManager {

    private static ActivityManager mInstance;
    private static boolean isInit = false;
    private  Stack<Activity> activityStack;

    public static ActivityManager getInstance() {
        if (!isInit) {
            throw new RuntimeException("ActivityManager没有初始化...");
        }
        return mInstance;
    }

    public static void init(Application application) {
        isInit = true;
        mInstance = new ActivityManager();
        mInstance.registerActivityLifecycleCallbacks(application);

    }

    private void registerActivityLifecycleCallbacks(Application application) {
        application.registerActivityLifecycleCallbacks(callbacks);
    }

    private ActivityManager() {
    }

    private void pushActivity(Activity activity) {
        if (activityStack == null)
            activityStack = new Stack<>();
        activityStack.add(activity);
    }

    private void popActivity(Activity activity) {
        if (activityStack != null && activity != null) {
            activityStack.remove(activity);
        }
    }

    public Activity peekActivity() {
        return activityStack.peek();
    }

    public Activity getTopActivity() {
        Activity activity = null;
        if (activityStack != null) {
            activity = activityStack.lastElement();
        }
        return activity;
    }

    public void popAllActivity() {
        if (activityStack == null) return;
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
    }




    private Application.ActivityLifecycleCallbacks callbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
           pushActivity(activity);
            Log.e("ActivityLifecycle","onActivityCreated:"+activity.getClass().getName());
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.e("ActivityLifecycle","onActivityDestroyed:"+activity.getClass().getName());
            popActivity(activity);
        }
    };
}
