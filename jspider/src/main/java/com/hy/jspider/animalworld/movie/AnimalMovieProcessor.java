package com.hy.jspider.animalworld.movie;

import org.apache.log4j.Logger;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author huangye
 */
public class AnimalMovieProcessor implements PageProcessor {

    public static final String START_URL = "http://www.1905.com/search/index-p-q-%E5%8A%A8%E7%89%A9.html?page=1";
    private static final Logger LOG = Logger.getLogger(AnimalMovieProcessor.class);

    private Site site = Site.me()
            .setRetryTimes(5)
            .setTimeOut(20 * 1000)
            .setSleepTime(1000);

    @Override
    public void process(Page page) {
        Selectable div = page.getHtml().xpath("//div[@class='shcontaner mt10']");

        List<String> names = div.$("img", "title").all();
        List<String> urls = div.$("img", "src").all();

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
