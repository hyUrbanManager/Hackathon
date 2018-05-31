package com.hy.jspider.baidu;

import com.hy.jspider.DbPipeline;

import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 邪恶漫画配置。
 *
 * @author hy 2018/5/18
 */
public class BaiduConfig {

    public static PageProcessor processor = new BaiduProcessor();

    public static DbPipeline pipeline = new BaiduPipeline();

    // e绅士首页最后一章地址。
    public static String startUrl = "https://www.baidu.com/s?wd=关键字";

}
