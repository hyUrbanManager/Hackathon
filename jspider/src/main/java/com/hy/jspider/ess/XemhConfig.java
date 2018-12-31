package com.hy.jspider.ess;

import com.hy.jspider.DbPipeline;

import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 邪恶漫画配置。
 *
 * @author hy 2018/5/18
 */
public class XemhConfig {

    public static PageProcessor processor = new XemhProcessor();

    public static DbPipeline pipeline = new XemhPipeline();

    // e绅士首页最后一章地址。
    public static String startUrl = "http://www.ess32.com/xiee/9282.html";

}
