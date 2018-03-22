package com.hy.media;

import android.app.Application;

/**
 * Created by Administrator on 2018/3/22.
 *
 * @author hy 2018/3/22
 */
public class MediaApplication extends Application {

    public static MediaApplication application;

    public static MediaApplication getInstance() {
        if (application == null) {
            throw new RuntimeException("application is null, have not initial.");
        }
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (application != null) {
            throw new RuntimeException("application onCreate has been called twice.");
        }
        application = this;
    }
}
