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

    public static String[] keywords = {
            "Android",
    };

    public static int keywordIndex = 0;

    public static PageProcessor processor = new GithubProcessor();

    public static DbPipeline pipeline = new GithubPipeline();

    // 第一个搜索地址。
    public static String startUrl = "https://github.com/search?q=" + keywords[keywordIndex];

}
