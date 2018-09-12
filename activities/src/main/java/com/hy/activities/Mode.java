package com.hy.activities;

import android.support.annotation.Nullable;

/**
 * Created by huangye on 2018/9/10.
 *
 * @author hy 2018/9/10
 */
public class Mode {

    static Mode sMode = new Mode();

    public static Mode getInstance() {
        return sMode;
    }

    RunningActivity mRunningActivity;

    public void setRunningActivity(@Nullable RunningActivity runningActivity) {
        mRunningActivity = runningActivity;
    }
}
