package com.hy.jspider;

import com.hy.jspider.baidu.BaiduConfig;
import com.hy.jspider.ess.XemhConfig;
import com.hy.jspider.github.GithubConfig;

import org.apache.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 爬虫运行。
 * github爬虫，15天运行一次。
 *
 * @author hy 2018/5/17
 */
public class MainScrape {

    private static Logger logger = Logger.getLogger(MainScrape.class);

    public static Spider spider;

    // spider.
    public static PageProcessor pageProcessor = GithubConfig.processor;
    public static DbPipeline pipeline = GithubConfig.pipeline;
    public static String startUrl = GithubConfig.startUrl;

    // exec.1天间隔定时，3倍软件定时。如果上次的没爬完，则等待下一个3天。
    public static final int execPeriodMills = 24 * 3600 * 1000;
    public static final int execPeriodDay = 3;
    public static int dayCnt = execPeriodDay - 1;

    // 是否测试环境，立即运行。
    private static boolean isTestScrape = false;

    // 爬虫是否正在运行。
    private static boolean isSpiderRunning = false;

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
                if (++dayCnt >= execPeriodDay) {
                    dayCnt = 0;

                    if (!isSpiderRunning) {
                        Light.programRunning();
                        logger.info("Three day, it is time to start spider task.");
                        isSpiderRunning = true;

                        // index = 0;
                        GithubConfig.keywordIndex = 0;

                        spider = Spider.create(pageProcessor);
                        spider.setDownloader(new OkHttpDownloader())
                                .addUrl(startUrl)
                                // 单线程。
//                            .thread(Runtime.getRuntime().availableProcessors())
                                .addPipeline(new ConsolePipeline())
                                .addPipeline(pipeline)
                                .addPipeline(new SpiderEndListener())
                                .run();
                    } else {
                        logger.info("spider is still running, wait for next three day.");
                    }
                }
            }
        }, scheduleDate, execPeriodMills);

        // 增加退出钩子。
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Light.programExit();

            timer.cancel();
            pipeline.endDb();
            String exitTime = new Date().toString() + " exit jvm.";
            Logger.getLogger(Main.class).info(exitTime);
        }));
    }

    static class SpiderEndListener implements Pipeline, Closeable {
        @Override
        public void process(ResultItems resultItems, Task task) {
        }

        @Override
        public void close() throws IOException {
            isSpiderRunning = false;
        }
    }

}
