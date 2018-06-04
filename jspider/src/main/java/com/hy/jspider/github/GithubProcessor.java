package com.hy.jspider.github;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Administrator on 2018/5/10.
 *
 * @author hy 2018/5/10
 */
public class GithubProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        page.putField("text", page.getRawText());

//        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
