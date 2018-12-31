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

    public static final String baseUrl = "https://www.github.com";

    public static final String[] keywords = {
            "Android",
            "java",
            "android c++",
            "flutter",
            "react native",
            "rx",
            "kotlin",
            "gradle",
            "okhttp",
            "glide",
            "raspberry",
            "spring",
            "google",
            "facebook",
            "microSoft",
            "tencent",
            "alibaba",
            "baidu",
            "iflytek",
            "cvte",
    };

    public static int keywordIndex = 0;

    public static final PageProcessor processor = new GithubProcessor();

    public static final DbPipeline pipeline = new GithubPipeline();

    // 第一个搜索地址。
    public static final String startUrl = "https://www.github.com/search?q=" + keywords[keywordIndex];

}
