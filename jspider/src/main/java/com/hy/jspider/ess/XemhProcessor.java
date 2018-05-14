package com.hy.jspider.ess;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * 爬取所有下载链接。
 *
 * @author hy 2018/5/10
 */
public class XemhProcessor implements PageProcessor {

    private Site site = Site.me()
            .setRetryTimes(5)
            .setTimeOut(20 * 1000)
            .setSleepTime(1000);

    int num = 1;

    @Override
    public void process(Page page) {
        Selectable comicNode = page.getHtml().xpath("//li[@id='imgshow']/img");
        Selectable nextNode = page.getHtml().xpath("//a[@id='pre2']");

//        String s = page.getHtml().xpath("//[@class='tagc2]/text()").get();
//        String s = page.getHtml().xpath("//li[@id='imgshow']/img/@href").get();
//        String s = page.getHtml().css("").get();
//        System.out.println(s);
//        String n = comicNode.xpath("@alt").get();
//        page.putField("name", comicNode.xpath("@alt").get());
//        page.putField("url", comicNode.xpath("@src").get());

        page.putField("title", comicNode.regex("alt=\"(.*?)\"", 1).get());
        page.putField("url", comicNode.regex("src=\"(.*?)\"", 1).get());
        page.putField("next", page.getHtml().regex("上一篇.<a href='(.*?)'>", 1).get());
        String next = page.getHtml().regex("上一篇.<a href='(.*?)'>", 1).get();

        // 统计。
        page.putField("num", num++);

        page.addTargetRequest("http://www.ess32.com" + next);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
