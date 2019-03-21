package com.hy.jspider.animalworld.sense;

import org.apache.log4j.Logger;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author huangye
 */
public class AnimalSenseProcessor implements PageProcessor {

    public static final String START_URL = "http://www.iltaw.com/knowledge";
    private static final Logger LOG = Logger.getLogger(AnimalSenseProcessor.class);

    private Site site = Site.me()
            .setRetryTimes(5)
            .setTimeOut(20 * 1000)
            .setSleepTime(1000);

    @Override
    public void process(Page page) {
        if (page.getUrl().get().equals(START_URL)) {
            Selectable div = page.getHtml().xpath("//h3[@class='clearfix']");
            List<String> urls = div.links().all();
            page.addTargetRequests(urls);
            return;
        }

        String title = page.getHtml().xpath("//div[@class='right-wrap']")
                .$("div.title", "text").get();
        String content = page.getHtml().$("div.description", "text").get();

        LOG.info(title);
        LOG.info(content.substring(0, 10));

        page.putField("title", title);
        page.putField("content", content);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
