package com.hy.jspider.animalworld.book;

import org.apache.log4j.Logger;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author huangye
 */
public class AnimalBookProcessor implements PageProcessor {

    public static final String START_URL = "http://search.dangdang.com/?key=%B6%AF%CE%EF&act=input";
    private static final Logger LOG = Logger.getLogger(AnimalBookProcessor.class);

    private Site site = Site.me()
            .setRetryTimes(5)
            .setTimeOut(20 * 1000)
            .setSleepTime(1000);

    @Override
    public void process(Page page) {
        Selectable div = page.getHtml().xpath("//div[@class='con shoplist']");

        List<String> names = div.$("img", "alt").all();
        List<String> urls = div.$("img", "data-original").all();

        names.remove(0);
        urls.remove(0);

        LOG.info(names);
        LOG.info(urls);

        LOG.info(names.size() + " , " + urls.size());

        page.putField("names", names);
        page.putField("urls", urls);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
