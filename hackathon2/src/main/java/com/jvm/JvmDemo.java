package com.jvm;

import org.junit.Test;

/**
 * 尝试jvm特性。守护线程
 *
 * @author huangye
 */
public class JvmDemo {

    public class PrintThread extends Thread {
        private int mTimes;

        public PrintThread(int times) {
            mTimes = times;
        }

        @Override
        public void run() {
            for (int i = 0; i < mTimes; i++) {
                System.out.println("--- printf. " + Thread.currentThread().getName() + " ---");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test1() {
        Thread subThread = new PrintThread(10000);
        subThread.setName("subThread 1");
        subThread.setDaemon(true);
        subThread.start();
        Thread subThread2 = new PrintThread(3);
        subThread.setName("subThread 2");
        subThread2.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
