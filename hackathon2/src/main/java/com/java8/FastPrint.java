package com.java8;

/**
 * Created by Administrator on 2017/10/29.
 *
 * @author hy 2017/10/29
 */
public interface FastPrint {

    default void p() {
        System.out.println();
    }

    default void p(String msg) {
        if (msg == null) {
            System.out.println("null");
            return;
        }
        System.out.println(msg);
    }
}
