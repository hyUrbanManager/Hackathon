package com.hook;

import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * Hook java 测试
 *
 * @author hy 2018/3/7
 */
public class HookTest {

    @Test
    public void test() {
        IActivityManager activityManager = new ActivityManagerService();
        String result = activityManager.startActivity("test");
        System.out.println("result: " + result);
    }

    @Test
    public void test2() {
        HyHookHandler handler = new HyHookHandler(new ActivityManagerService());
        IActivityManager activityManager = (IActivityManager) Proxy.newProxyInstance(
                IActivityManager.class.getClassLoader(),
                new Class[]{IActivityManager.class},
                handler);
        String result = activityManager.startActivity("test2");
        System.out.println("result: " + result);
    }

    @Test
    public void test3() {
        HyHookHandler handler = new HyHookHandler(new ActivityManagerService());

    }
}
