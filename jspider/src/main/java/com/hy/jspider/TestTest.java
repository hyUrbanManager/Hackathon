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
        Main.main(new String[]{"-s", "-t"});
        try {
            Thread.sleep(4 * 3600 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        String s = "21312'rwe";
        String a = s.replaceAll("'", "\\\\'");
        System.out.println(a);
    }
}
