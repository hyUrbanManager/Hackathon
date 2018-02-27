package com.hy.androidlib;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 判断APP是前台还是后台
 *
 * @author zqjasonZhong
 *         date : 2018/2/6
 * @source http://steveliles.github.io/is_my_android_app_currently_foreground_or_background.html
 */
public class Foreground implements Application.ActivityLifecycleCallbacks{

    public static final String TAG = Foreground.class.getName();

    public interface Listener {

        void onBecameForeground();

        void onBecameBackground();

    }

    private int resumed;
    private int paused;
    private int started;
    private int stopped;

    private List<Listener> listeners = new CopyOnWriteArrayList<>();
    private Handler mHandler = new Handler();
    private static Foreground instance;

    /**
     * Its not strictly necessary to use this method - _usually_ invoking
     * get with a Context gives us a path to retrieve the Application and
     * initialise, but sometimes (e.g. in test harness) the ApplicationContext
     * is != the Application, and the docs make no guarantees.
     *
     * @param application
     * @return an initialised Foreground instance
     */
    public static Foreground init(Application application){
        if (instance == null) {
            instance = new Foreground();
            application.registerActivityLifecycleCallbacks(instance);
        }
        return instance;
    }

    public static Foreground get(Application application){
        if (instance == null) {
            init(application);
        }
        return instance;
    }

    public static Foreground get(Context ctx){
        if (instance == null) {
            Context appCtx = ctx.getApplicationContext();
            if (appCtx instanceof Application) {
                init((Application)appCtx);
            }else {
                throw new IllegalStateException(
                        "Foreground is not initialised and " +
                                "cannot obtain the Application object");
            }
        }
        return instance;
    }

    public static Foreground get(){
        if (instance == null) {
            throw new IllegalStateException(
                    "Foreground is not initialised - invoke " +
                            "at least once with parameterised init/get");
        }
        return instance;
    }

    public boolean isForeground(){
        return resumed > paused;
    }

    public boolean isBackground(){
        return !(started > stopped);
    }

    public void addListener(Listener listener){
        if(listeners != null && listener != null){
            listeners.add(listener);
        }
    }

    public void removeListener(Listener listener){
        if(listeners != null && listener != null){
            listeners.remove(listener);
        }
    }

    private void notifyEvent(final int type){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(listeners != null){
                    for (Listener listener : listeners){
                        if(type == 0){
                            listener.onBecameForeground();
                        }else{
                            listener.onBecameBackground();
                        }
                    }
                }
            }
        });
    }

    public void release(){
        instance = null;
        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
        }
        if(listeners != null){
            listeners.clear();
            listeners = null;
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ++resumed;
        if(resumed > paused){
            notifyEvent(0);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ++paused;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        ++started;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ++stopped;
        if(started <= stopped){
            notifyEvent(1);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
