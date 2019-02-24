package com.hy.jspider.animalworld.news;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author huangye
 */
public class AnimalNewsProcessor implements PageProcessor {

    public static final String START_URL = "http://www.iltaw.com/news";
    private static final Logger LOG = Logger.getLogger(AnimalNewsProcessor.class);

    private Site site = Site.me()
            .setRetryTimes(5)
            .setTimeOut(20 * 1000)
            .setSleepTime(1000);

    @Override
    public void process(Page page) {
        List<String> links = page.getHtml().$("h3.clearfix").links().all();

        if (!links.isEmpty()) {
            LOG.info(links.size());
            LOG.info(Arrays.toString(links.toArray()));
            page.addTargetRequests(links);
            page.setSkip(true);
            return;
        }

        AnimalNews.AnimalNewsBean newsBean = new AnimalNews.AnimalNewsBean();
        Selectable newsDiv = page.getHtml().$("div.right-wrap");
        newsBean.title = newsDiv.$("div.title", "text").get();
        newsBean.time = newsDiv.$("div.time", "text").get();
        newsBean.content = newsDiv.$("div.description", "text").get();
        if (newsBean.content.isEmpty()) {
            // 带图片的新闻。
            newsBean.content = newsDiv.$("div.description").$("p", "text").all().get(1);
        }

        page.putField("news", newsBean);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
