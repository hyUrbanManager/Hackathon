package com.hy.jspider;

import org.junit.Test;

/**
 * 测试。
 *
 * @author hy 2018/5/17
 */
public class TestTest {

    /**
     * Android studio 测试。
     */
    @Test
    public void run() {
//        System.setProperty("javax.net.debug", "all");
        if (false) {
            Main.runSpider();
        } else {
            Main.main(new String[]{});
            try {
                Thread.sleep(3600 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
