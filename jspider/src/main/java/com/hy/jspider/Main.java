package com.hy.jspider;

import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 爬虫起始。
 * 开始爬虫，和控制系统定时。
 * 具有下载器功能。
 */
public class Main {

    // version.
    public static final String version = "0.1";

    // is linux system.
    public static boolean isLinux = true;

    /**
     * main.
     */
    public static void main(String[] args) {
        // 判断系统。
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            isLinux = false;
        }

        light(5);

        // init log4j.
        initLog4jConfig();

        if (args == null || args.length == 0) {
            printHelp();
        } else {
            printInfo();
            MainScrape.main(args);
        }
    }

    /**
     * 输出帮助。
     */
    public static void printHelp() {
        String help = "" +
                "use: option [args]\n" +
                "-s [startUrl] [-t](if test scrape) \n" +
                "-d \n";
        System.out.println(help);
    }

    /**
     * 输出程序重要信息。
     */
    public static void printInfo() {
        System.out.println("version: " + version);
        pwd();
        System.out.println(System.getProperty("os.name"));
        long totalMem = Runtime.getRuntime().totalMemory();
        long maxMem = Runtime.getRuntime().maxMemory();
        long freeMem = Runtime.getRuntime().freeMemory();
        System.out.println("totalMem: " + totalMem / 1024 / 1024 + " M.");
        System.out.println("maxMem: " + maxMem / 1024 / 1024 + " M.");
        System.out.println("freeMem: " + freeMem / 1024 / 1024 + " M.");
    }


    /**
     * 初始化日志参数，分为windows端日志和linux端日志。
     */
    private static void initLog4jConfig() {
        Properties props;
        FileInputStream fis = null;
        try {
            // 从配置文件dbinfo.properties中读取配置信息
            props = new Properties();
            if (isLinux) {
                fis = new FileInputStream("/home/hy/Public/log4jl.properties");
            } else {
                fis = new FileInputStream("jspider/log4jw.properties");
            }
            props.load(fis);
            PropertyConfigurator.configure(props);//装入log4j配置信息
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 输出当前路径。
     */
    private static void pwd() {
        File directory = new File("");//参数为空
        String courseFile = null;
        try {
            courseFile = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(courseFile);
    }

    /**
     * 亮几号灯。
     * 1~5为亮1到5号灯。
     * 其他数字则为熄灭。
     *
     * @param num
     */
    public static void light(int num) {
        if (isLinux) {
            try {
                Runtime.getRuntime().exec("bash /home/hy/light.sh " + num);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
