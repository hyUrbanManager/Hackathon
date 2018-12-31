package com.hy.jspider.github;

import com.hy.jspider.Light;
import com.hy.jspider.Main;
import com.hy.jspider.MainScrape;
import com.hy.jspider.OkHttpDownloader;

import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Headers;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Selectable;

/**
 * Created by Administrator on 2018/5/10.
 *
 * @author hy 2018/5/10
 */
public class GithubProcessor implements PageProcessor {

    private static final String DEBUG_KEY = "==========";
    private static final Logger logger = Logger.getLogger(GithubProcessor.class);

    // github防爬，沉睡时间设置为10s。网络中断容忍为60分钟。
    private Site site = Site.me()
            .setAcceptStatCode(getAcceptCode())
            .setCycleRetryTimes(6 * 60)
            .setRetrySleepTime(10 * 1000)
            .setSleepTime(200);

    private Set<Integer> getAcceptCode() {
        Set<Integer> set = new HashSet<>();
        set.add(200);
        set.add(429);
        return set;
    }

    @Override
    public void process(Page page) {
//        page.putField("text", page.getRawText());

        // 正常获取到页面，匹配，否则计算错误代码。
        if (OkHttpDownloader.getHttpCode() < 300) {
            Selectable tableDiv = page.getHtml().xpath("//ul[@class=\"repo-list\"]");

            List<String> titles = tableDiv.xpath("//div[@class=\"col-8 pr-3\"]/h3/a/@href").all();
            List<String> languages = tableDiv.xpath("//div[@class=\"d-table-cell col-2 text-gray pt-2\"]/text()").all();
            List<String> starses = tableDiv.xpath("//div[@class=\"col-2 text-right pt-1 pr-3 pt-2\"]/a/text()").all();

            // 严格匹配，不足的加入unknown。
            List<String> descriptions = tableDiv.xpath("//div[@class=\"col-8 pr-3\"]" +
                    "/p[@class=\"col-9 d-inline-block text-gray mb-2 pr-4\"]/text()").all();
            int ts = titles.size() - descriptions.size();
            for (int i = 0; i < ts; i++) {
                descriptions.add("*** unknown ***");
            }

            // 描述采用宽松匹配的方法，对于一些没有描述的项目采用后面的更新时间。
//          List<String> descriptions = tableDiv.xpath("//div[@class=\"col-8 pr-3\"]" +
//                "//p/text()").all();

            page.putField("keyword", GithubConfig.keywords[GithubConfig.keywordIndex]);
            page.putField("titles", titles);
            page.putField("languages", languages);
            page.putField("starses", starses);
            page.putField("descriptions", descriptions);

            // 是否还有下一页，下一个关键字，无则程序睡眠。
            Selectable nextDiv = page.getHtml().xpath("//div[@class=\"paginate-container\"]" +
                    "//a[@class=\"next_page\"]");
            String nextUrl;
            if (nextDiv.match()) {
                nextUrl = page.getHtml().xpath("//div[@class=\"paginate-container\"]" +
                        "//a[@class=\"next_page\"]/@href").get();
                page.putField("nextUrl", nextUrl);
                page.addTargetRequest(GithubConfig.baseUrl + nextUrl);
            } else if (++GithubConfig.keywordIndex < GithubConfig.keywords.length) {
                nextUrl = "/search?q=" + GithubConfig.keywords[GithubConfig.keywordIndex];
                page.addTargetRequest(GithubConfig.baseUrl + nextUrl);
            } else {
                // sleep.
                Light.programSleeping();
            }

        } else if (OkHttpDownloader.getHttpCode() == 429) {
            Headers headers = OkHttpDownloader.getHeaders();
            String ts = headers.get("Retry-After");

            // 获取成功则等待时间，否则继续尝试。
            int retryTime;
            try {
                retryTime = Integer.parseInt(ts.trim());
            } catch (Exception e) {
                retryTime = 0;
            }

            logger.info("retryTime: " + retryTime);
            sleep(retryTime * 1000);

            // 把失败的页面加入队列。
            QueueScheduler queueScheduler = (QueueScheduler) MainScrape.spider.getScheduler();
            queueScheduler.getDuplicateRemover().resetDuplicateCheck(getSite().toTask());
            page.addTargetRequest(OkHttpDownloader.getFailUrl());
        } else {
            logger.error("code: " + OkHttpDownloader.getHttpCode());
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void sleep(int timeMills) {
        try {
            Thread.sleep(timeMills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
