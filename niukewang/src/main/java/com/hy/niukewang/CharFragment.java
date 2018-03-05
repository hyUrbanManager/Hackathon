package com.hy.niukewang;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * 一个由小写字母组成的字符串可以看成一些同一字母的最大碎片组成的。例如,"aaabbaaac"是由下面碎片组成的:'aaa','bb','c'。牛牛现在给定一个字符串,请你帮助计算这个字符串的所有碎片的平均长度是多少。
 * <p>
 * 输入描述:
 * 输入包括一个字符串s,字符串s的长度length(1 ≤ length ≤ 50),s只含小写字母('a'-'z')
 * <p>
 * <p>
 * 输出描述:
 * 输出一个整数,表示所有碎片的平均长度,四舍五入保留两位小数。
 * <p>
 * 如样例所示: s = "aaabbaaac"
 * 所有碎片的平均长度 = (3 + 2 + 3 + 1) / 4 = 2.25
 * <p>
 * 输入例子1:
 * aaabbaaac
 * <p>
 * 输出例子1:
 * 2.25
 *
 * @author hy 2018/3/5
 */
public class CharFragment {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        String input = scanner.nextLine();
        String input = "aaabbaaac";

        char[] chars = input.toCharArray();
        List<Integer> list = new ArrayList<>();
        char cc = chars[0];
        int index = 1;
        int len = 1;
        while (index < chars.length) {
            char c = chars[index];
            if (c == cc) {
                len++;
            } else {
                cc = c;
                list.add(len);
                len = 1;
            }
            index++;
        }
        list.add(len);

        int total = 0;
        for (Integer i : list) {
            total += i;
        }
        float result = (float) total / list.size();
//        int r = Math.round(result * 100);
//        result = (float) r / 100;
//        System.out.println(result);

        System.out.println(String.format(Locale.CHINA,"%.2f", result));

    }

}













