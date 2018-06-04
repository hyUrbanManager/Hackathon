package com.hy.jspider.github;

import com.hy.jspider.DbPipeline;
import com.hy.jspider.ess.XemhPipeline;
import com.hy.jspider.ess.XemhProcessor;

import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 邪恶漫画配置。
 *
 * @author hy 2018/5/18
 */
public class GithubConfig {

    public static PageProcessor processor = new GithubProcessor();

    public static DbPipeline pipeline = new GithubPipeline();

    // e绅士首页最后一章地址。
    public static String startUrl = "https://github.com/search?q=Android";

}
