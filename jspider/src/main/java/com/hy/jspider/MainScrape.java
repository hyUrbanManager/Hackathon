package com.hy.jspider;

import com.hy.jspider.baidu.BaiduConfig;
import com.hy.jspider.ess.XemhConfig;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 爬虫运行。
 *
 * @author hy 2018/5/17
 */
public class MainScrape {

    // spider.
    public static PageProcessor pageProcessor = BaiduConfig.processor;
    public static DbPipeline pipeline = BaiduConfig.pipeline;
    public static String startUrl = BaiduConfig.startUrl;

    // exec.
    public static final int execPeriodMills = 24 * 3600 * 1000;

    private static boolean isTestScrape = false;

    /**
     * Scrape，从jvm Main传来的参数。
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            int index = 0;
            String s;
            String ss;
            while (index < args.length && (s = args[index++]).startsWith("-")) {
                switch (s) {
                    case "-s":
                        if ((ss = args[index++]).startsWith("-")) {
                            index--;
                        } else {
                            startUrl = ss;
                        }
                        break;
                    case "-t":
                        isTestScrape = true;
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("*** " + Arrays.toString(args) + " args is error. ***");
            return;
        }

        // 定时任务，每天晚上3点开始执行。
        Timer timer = new Timer();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date scheduleDate = calendar.getTime();
        if (!isTestScrape && scheduleDate.before(now)) {
            // do nothing.exec now.
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            scheduleDate = calendar.getTime();
        }

        // start db.
        pipeline.startDb();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Logger.getLogger(Main.class).info("start spider task.");
                Spider.create(pageProcessor)
                        .addUrl(startUrl)
                        .thread(Runtime.getRuntime().availableProcessors())
                        .addPipeline(new ConsolePipeline())
                        .addPipeline(pipeline)
                        .run();
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
}
