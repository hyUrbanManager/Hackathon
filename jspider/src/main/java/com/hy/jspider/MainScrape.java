package com.hy.jspider;

import com.hy.jspider.ess.XemhPipeline;
import com.hy.jspider.ess.XemhProcessor;

import org.apache.log4j.Logger;

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
    public static PageProcessor pageProcessor = new XemhProcessor();
    public static DbPipeline pipeline = new XemhPipeline();
    public static String startUrl = "http://www.ess32.com/xiee/9282.html";

    // exec.
    public static final int execPeriodMills = 24 * 3600 * 1000;

    /**
     * Scrape，从jvm Main传来的参数。
     * @param args
     */
    public static void main(String[] args) {
        if (args.length >= 2) {
            startUrl = args[1];
        }

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
                Spider.create(pageProcessor)
                        .addUrl(startUrl)
                        .thread(Runtime.getRuntime().availableProcessors())
                        .addPipeline(new ConsolePipeline())
                        .addPipeline(pipeline);
//                        .run();
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
