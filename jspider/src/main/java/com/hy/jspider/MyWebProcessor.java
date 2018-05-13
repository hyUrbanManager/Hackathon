package com.hy.jspider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Administrator on 2018/5/10.
 *
 * @author hy 2018/5/10
 */
public class MyWebProcessor implements PageProcessor{

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        page.putField("h1", page.getHtml().xpath("//h1/a/@href").get());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
