package com.hy.jspider.animalworld;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author huangye
 */
public class AnimalProcessor implements PageProcessor {

    private static final Logger LOG = Logger.getLogger("AnimalProcessor");

    private static final String URL_PREFIX = "http://www.iltaw.com/animal/";
    private static final String[] ANIMAL_CATEGORY = {
            "mammal", "birds", "fish", "amphibians", "reptile", "invertebrates",
    };
    private static final String[] ANIMAL_CATEGORY_NAME = {
            "哺乳动物", "鸟类", "鱼类", "两栖动物", "爬行动物", "无脊椎动物",
    };
    private static final int MAX_PAGES = 2;

    public static final String START_URL = URL_PREFIX + ANIMAL_CATEGORY[0];

    private Site site = Site.me()
            .setRetryTimes(5)
            .setTimeOut(20 * 1000)
            .setSleepTime(1000);

    private int mPages = 1;
    private int index = 0;

    @Override
    public void process(Page page) {
        List<String> imgList = page.getHtml().$("div.image-wrap")
                .regex("data-url=\"(http://image.iltaw.com/.*?)\"", 1).all();
        List<String> nameList = page.getHtml().$("div.text-wrap")
                .regex("target=\"_blank\">(.*?)<br>(.*?)</a>", 1).all();
        List<String> enNameList = page.getHtml().$("div.text-wrap")
                .regex("target=\"_blank\">(.*?)<br>(.*?)</a>", 2).all();
        List<String> descList = page.getHtml().$("div.text-wrap")
                .regex("<p>(.*)</p>", 1).all();

        page.putField("id", index + 1);
        page.putField("title", ANIMAL_CATEGORY_NAME[index]);
        page.putField("imgUrlList", imgList);
        page.putField("nameList", nameList);
        page.putField("enNameList", enNameList);
        page.putField("descList", descList);

        // 统计。
        LOG.info(Arrays.toString(imgList.toArray()));
        LOG.info(Arrays.toString(nameList.toArray()));
        LOG.info(Arrays.toString(enNameList.toArray()));
        LOG.info(Arrays.toString(descList.toArray()));
        LOG.info(ANIMAL_CATEGORY[index] + " 已经捕获了" + (mPages++) + "页");

        if (mPages <= MAX_PAGES) {
            page.addTargetRequest(URL_PREFIX + ANIMAL_CATEGORY[index] + "?page=" + mPages);
        } else if (index < ANIMAL_CATEGORY.length - 1) {
            mPages = 0;
            page.addTargetRequest(URL_PREFIX + ANIMAL_CATEGORY[++index] + "?page=" + mPages);
        } else {
            // 结束爬取。
            LOG.info("end");
            page.putField("end", true);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
