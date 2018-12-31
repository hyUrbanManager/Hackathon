package com.hy.jspider;

import java.io.IOException;

/**
 * 由于灯光较为复杂，单独射类控制灯光。
 *
 * @author hy 2018/6/12
 */
public class Light {

    /**
     * java爬虫正在运行。
     */
    public static void programRunning() {
        light(5); //白灯。
    }

    /**
     * java程序退出。
     */
    public static void programExit() {
        light(9); //灭灯。
    }

    /**
     * java休眠中，等待下一次运行。
     */
    public static void programSleeping() {
        light(2); //黄灯。
    }

    /**
     * 正在获取Http数据。
     */
    public static void programAcquiringHttp() {
        light(4); //绿灯。
    }

    /**
     * 获取Http数据不正常。
     */
    public static void programHttpError() {
        light(1); //红灯。
    }

    /**
     * 亮几号灯。
     * 1~5为亮1到5号灯。
     * 其他数字则为熄灭。
     *
     * @param num
     */
    public static void light(int num) {
        if (Main.isLinux) {
            try {
                Runtime.getRuntime().exec("bash /home/hy/light.sh " + num);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
