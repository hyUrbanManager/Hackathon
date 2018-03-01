package com;

import org.junit.Test;

/**
 * Created by Administrator on 2018/3/1.
 *
 * @author hy 2018/3/1
 */
public class JavaTest {

    @Test
    public void test() {
        System.out.println("start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("finally");
        }
    }

}
