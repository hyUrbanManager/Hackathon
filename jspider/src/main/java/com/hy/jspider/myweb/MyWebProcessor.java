package com.hy.jspider.myweb;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 爬取所有下载链接。
 *
 * @author hy 2018/5/10
 */
public class MyWebProcessor implements PageProcessor{

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        List<String> list = page.getHtml().xpath("//a/@href").all();
        List<String> list2 = new ArrayList<>();
        for (String link : list) {
            if (link.contains(".")) {
                list2.add(link);
            }
        }

        page.putField("links", list2);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
