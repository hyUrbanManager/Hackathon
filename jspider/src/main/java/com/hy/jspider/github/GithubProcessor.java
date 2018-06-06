package com.hy.jspider.github;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * Created by Administrator on 2018/5/10.
 *
 * @author hy 2018/5/10
 */
public class GithubProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
//        page.putField("text", page.getRawText());

        Selectable table = page.getHtml().xpath("//ul[@class=\"repo-list\"]");

        String title = table.xpath("//h3/a/text()").get().replace("<em>", "").replace("</em>", "");
        String language = table.xpath("//div[@class=\"d-table-cell*\"]/text()").get();
        String stars = table.xpath("//a[@class=\"muted-link\"]/text()").get();
        String description = table.xpath("//p/text()").get();

        page.putField("keyword", GithubConfig.keywords[GithubConfig.keywordIndex]);
        page.putField("title", title);
        page.putField("language", language);
        page.putField("stars", stars);
        page.putField("description", description);

//        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
