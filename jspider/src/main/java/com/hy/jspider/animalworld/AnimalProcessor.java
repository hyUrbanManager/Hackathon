package com.hy.jspider.animalworld;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author huangye
 */
public class AnimalProcessor implements PageProcessor {

    private Site site = Site.me()
            .setRetryTimes(5)
            .setTimeOut(20 * 1000)
            .setSleepTime(1000);

    int num = 1;

    @Override
    public void process(Page page) {
        Selectable comicNode = page.getHtml().xpath("//h3[@class='t']/a");

        page.putField("title", comicNode.regex(">(.*?)</a>", 1).get());

        // 统计。
        page.putField("num", num++);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
