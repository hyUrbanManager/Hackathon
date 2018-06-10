package com.hy.jspider.github;

import com.hy.jspider.Main;
import com.hy.jspider.OkHttpDownloader;

import java.util.List;

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

    // github防爬，沉睡时间设置为10s。
    private Site site = Site.me().setRetryTimes(3).setSleepTime(10 * 1000);

    @Override
    public void process(Page page) {
//        page.putField("text", page.getRawText());

        Selectable tableDiv = page.getHtml().xpath("//ul[@class=\"repo-list\"]");

        List<String> titles = tableDiv.xpath("//div[@class=\"col-8 pr-3\"]/h3/a/@href").all();
        List<String> languages = tableDiv.xpath("//div[@class=\"d-table-cell col-2 text-gray pt-2\"]/text()").all();
        List<String> starses = tableDiv.xpath("//div[@class=\"col-2 text-right pt-1 pr-3 pt-2\"]/a/text()").all();

        // 严格匹配，不足的加入unknown。
        List<String> descriptions = tableDiv.xpath("//div[@class=\"col-8 pr-3\"]" +
                "/p[@class=\"col-9 d-inline-block text-gray mb-2 pr-4\"]/text()").all();
        int ts = titles.size() - descriptions.size();
        for (int i = 0; i < ts; i++) {
            descriptions.add("*** unknown ***");
        }

        // 描述采用宽松匹配的方法，对于一些没有描述的项目采用后面的更新时间。
//        List<String> descriptions = tableDiv.xpath("//div[@class=\"col-8 pr-3\"]" +
//                "//p/text()").all();

        page.putField("keyword", GithubConfig.keywords[GithubConfig.keywordIndex]);
        page.putField("titles", titles);
        page.putField("languages", languages);
        page.putField("starses", starses);
        page.putField("descriptions", descriptions);

        // 下一页。
        Selectable nextDiv = page.getHtml().xpath("//div[@class=\"paginate-container\"]" +
                "//a[@class=\"next_page\"]");
        String nextUrl = "";
        boolean isHaveNextUrl = true;

        // 是否还有下一页。
        if (nextDiv.match()) {
            nextUrl = page.getHtml().xpath("//div[@class=\"paginate-container\"]" +
                    "//a[@class=\"next_page\"]/@href").get();
            page.putField("nextUrl", nextUrl);
        } else {
            if (++GithubConfig.keywordIndex < GithubConfig.keywords.length) {
                nextUrl = "search?q=" + GithubConfig.keywords[GithubConfig.keywordIndex];
            } else {
                isHaveNextUrl = false;
            }
        }

        if (isHaveNextUrl) {
            page.addTargetRequest(GithubConfig.baseUrl + nextUrl);
        } else {
            // 把失败的页面加入队列。
            if (OkHttpDownloader.isFail) {
                page.addTargetRequest(OkHttpDownloader.failUrl);
            } else {
                // sleep.
                Main.light(2);
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
