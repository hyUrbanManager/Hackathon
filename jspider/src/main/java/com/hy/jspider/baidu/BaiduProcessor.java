package com.hy.jspider.baidu;

import org.apache.log4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * 爬取所有下载链接。
 *
 * @author hy 2018/5/10
 */
public class BaiduProcessor implements PageProcessor {

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

