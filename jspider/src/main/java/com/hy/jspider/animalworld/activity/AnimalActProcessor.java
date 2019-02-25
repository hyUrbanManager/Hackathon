package com.hy.jspider.animalworld.activity;

import org.apache.log4j.Logger;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author huangye
 */
public class AnimalActProcessor implements PageProcessor {

    public static final String START_URL = "http://www.fon.org.cn/index.php?option=com_k2&view=itemlist&layout=category&task=category&id=3&Itemid=110";
    private static final Logger LOG = Logger.getLogger(AnimalActProcessor.class);

    private Site site = Site.me()
            .setRetryTimes(5)
            .setTimeOut(20 * 1000)
            .setSleepTime(1000);

    @Override
    public void process(Page page) {
        Selectable div = page.getHtml().xpath("//div[@id='itemListLeading']");

        List<String> urls = div.$("img", "src").all();
        List<String> titles = div.$("strong.fw-n", "text").all();
        List<String> contents = div.$("p.c-666666", "text").all();
        List<String> dates = div.$("span.c-999999", "text").all();

        LOG.info(urls);
        LOG.info(titles);
        LOG.info(contents);
        LOG.info(dates);

        LOG.info(urls.size() + " , " + titles.size() + " , " + contents.size() + " , " + dates.size());

        page.putField("urls", urls);
        page.putField("titles", titles);
        page.putField("contents", contents);
        page.putField("dates", dates);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
