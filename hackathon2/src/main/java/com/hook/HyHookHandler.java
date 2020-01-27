package com.hook;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

public class HyHookHandler implements InvocationHandler {

    private static final String TAG = "HyHookHandler";

    private IActivityManager mProxyActivityManager;
    private IActivityManager mInnerActivityManager;

    public HyHookHandler(IActivityManager proxyActivityManager) {
        mProxyActivityManager = proxyActivityManager;
        mInnerActivityManager = name -> TAG + "_" + name;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        System.out.println(TAG + ": " + method + ", " + Arrays.toString(objects));

        long ran = new Random().nextInt(2);
        if (ran >= 1) {
            System.out.println("intercept.");
            return method.invoke(mInnerActivityManager, objects);
        } else {
            System.out.println("not intercept.");
            return method.invoke(mProxyActivityManager, objects);
        }
    }
}
