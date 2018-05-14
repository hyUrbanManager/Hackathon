package com.hy.jspider;

import com.hy.jspider.ess.XemhPipeline;
import com.hy.jspider.ess.XemhProcessor;
import com.hy.jspider.myweb.MyPipeline;
import com.hy.jspider.myweb.MyWebProcessor;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 爬虫起始。
 * 开始爬虫，和控制系统定时。
 */
public class Main {

    /**
     * Android studio 测试。
     */
    @Test
    public void run() {
//        System.setProperty("javax.net.debug", "all");
        if (false) {
            runSpider();
        } else {
            main(null);
            try {
                Thread.sleep(3600 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // version.
    public static final String version = "0.1";

    // spider.
    public static PageProcessor pageProcessor = new XemhProcessor();
    public static DbPipeline pipeline = new XemhPipeline();
    public static String startUrl = "http://www.ess32.com/xiee/9282.html";

    // exec.
//    public static final
    public static final int execPeriodMills = 24 * 3600 * 1000;

    /**
     * main.
     */
    public static void main(String[] args) {
        // init log4j.
        initLog4j();

        // 定时任务，每天晚上3点开始执行。
        Timer timer = new Timer();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date scheduleDate = calendar.getTime();
        if (scheduleDate.after(now)) {
            // do nothing.exec now.
        }

        // start db.
        pipeline.startDb();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Logger.getLogger(Main.class).info("start spider task.");
                runSpider();
            }
        }, scheduleDate, execPeriodMills);

        // 增加退出钩子。
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            timer.cancel();
            pipeline.endDb();
            String exitTime = new Date().toString() + " exit jvm.";
            Logger.getLogger(Main.class).info(exitTime);
        }));

    }

    public static void runSpider() {
        System.out.println("version: " + version);

        pwd();

        Spider.create(pageProcessor)
                .addUrl(startUrl)
                .thread(4)
                .addPipeline(new ConsolePipeline())
                .addPipeline(pipeline)
                .run();
    }

    public static void initLog4j() {
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println("operating system: " + os);

        if (os.contains("windows")) {
            initLog4jConfig(true);
        } else {
            initLog4jConfig(false);
        }
    }

    /**
     * 初始化日志参数，分为windows端日志和linux端日志。
     *
     * @param isWindows
     */
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

}
