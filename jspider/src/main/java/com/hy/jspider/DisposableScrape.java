package com.hy.jspider;

import com.hy.jspider.animalworld.sense.AnimalSensePipeline;
import com.hy.jspider.animalworld.sense.AnimalSenseProcessor;

import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import us.codecraft.webmagic.Spider;

/**
 * 一次性爬虫，一次性爬取。
 *
 * @author huangye
 */
public class DisposableScrape {

    public static final String START_URL = AnimalSenseProcessor.START_URL;

    public static void main(String[] args) {
        initLog4jConfig();

        Spider.create(new AnimalSenseProcessor())
                .setDownloader(new OkHttpDownloader())
                .addUrl(START_URL)
                .thread(Runtime.getRuntime().availableProcessors())
                .addPipeline(new AnimalSensePipeline())
                .run();
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
            fis = new FileInputStream("/Users/huangye/AndroidStudioProject3/Hackathon/jspider/log4j_mac.properties");
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


}
