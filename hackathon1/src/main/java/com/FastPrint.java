package com;

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

    default void pp(String msg) {
        if (msg == null) {
            System.out.print("null");
            return;
        }
        System.out.print(msg);
    }

    default long startTimer() {
        return System.currentTimeMillis();
    }

    default void endTimer(long startTime) {
        p();
        p();
        p("use time: " + (System.currentTimeMillis() - startTime));
    }
}
