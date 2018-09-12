package com.hy.parser;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式合集。
 *
 * @author hy 2018/8/27
 */
public class RegexSet {

    public String[] ss = {
            "123123213@qq.com",
            "sDq12l3k12@163.com",
            "123123213@qq.cm",
            "1$@#)@q.",
            "我不是邮箱",
            "321OOPP21h412qq.com",
            "@",
            "@.",
            "",
    };

    @Test
    public void test1() {
//        Pattern pattern = Pattern.compile("\\w+?@[a-zA-Z0-9]]+?.com");
        Pattern pattern = Pattern.compile("\\w+?@[a-zA-Z|0-9]+?.com");
        for (String s : ss) {
            Matcher matcher = pattern.matcher(s);
            System.out.println("" + matcher.find());
        }
    }


}
