package com.hy.jspider;

import com.hy.jspider.myweb.DatabasePipeline;
import com.hy.jspider.myweb.MyWebProcessor;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 爬虫起始。
 */
public class Main {

    public static final String version = "0.1";

    public static PageProcessor pageProcessor = new MyWebProcessor();
    public static Pipeline pipeline = new DatabasePipeline();
    public static String startUrl = "http://139.199.170.98";

    @Test
    public void run() {
//        System.setProperty("javax.net.debug", "all");
        main(new String[]{"123"});
    }

    public static void main(String[] args) {
        System.out.println("version: " + version);

        pwd();

        String os = System.getProperty("os.name").toLowerCase();
        System.out.println("operating system: " + os);

        if (os.contains("windows")) {
            initLog4jConfig(true);
        } else {
            initLog4jConfig(false);
        }

        Spider.create(pageProcessor)
                .addUrl(startUrl)
                .thread(4)
                .addPipeline(new ConsolePipeline())
                .addPipeline(pipeline)
                .run();
    }

    private static void initLog4jConfig(boolean isWindows) {
        Properties props;
        FileInputStream fis = null;
        try {
            // 从配置文件dbinfo.properties中读取配置信息
            props = new Properties();
            if (isWindows) {
                fis = new FileInputStream("jspider/log4jw.properties");
            } else {
                fis = new FileInputStream("/home/hy/Public/log4jl.properties");
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

}
