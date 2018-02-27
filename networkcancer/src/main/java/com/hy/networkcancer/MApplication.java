package com.hy.networkcancer;

import android.app.Application;

import com.hy.androidlib.network.WifiHelper;
import com.hy.androidlib.utils.ToastUtil;

/**
 * Created by Administrator on 2018/2/27.
 *
 * @author hy 2018/2/27
 */
public class MApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        WifiHelper.init(this.getApplicationContext());
        ToastUtil.init(this.getApplicationContext());
    }
}
