package com.hy.niukewang;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * 程序入口。
 *
 * @author hy 2018/3/5
 */
public class JavaExec {

    public static String className = "com.hy.niukewang.Chorus";

    @Test
    public void start() throws Exception {
        Class clazz = Class.forName(className);
        Method method = clazz.getMethod("main", String[].class);

        long start = System.currentTimeMillis();

        String[] args = {"niukewang"};
        method.invoke(this, (Object) args);

        System.out.println("used time: " + (System.currentTimeMillis() - start));

    }

}
