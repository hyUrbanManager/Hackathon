package com.hy.niukewang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * 为了得到一个数的"相反数",我们将这个数的数字顺序颠倒,然后再加上原先的数得到"相反数"。例如,为了得到1325的"相反数",首先我们将该数的数字顺序颠倒,我们得到5231,之后再加上原先的数,我们得到5231+1325=6556.如果颠倒之后的数字有前缀零,前缀零将会被忽略。例如n = 100, 颠倒之后是1.
 * 输入描述:
 * 输入包括一个整数n,(1 ≤ n ≤ 10^5)
 * <p>
 * <p>
 * 输出描述:
 * 输出一个整数,表示n的相反数
 * <p>
 * 输入例子1:
 * 1325
 * <p>
 * 输出例子1:
 * 6556
 *
 * @author hy 2018/3/5
 */
public class OppositeNumber {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        String input = scanner.nextLine();
//        int num = Integer.parseInt(input);
        int num = 1325;

        List<Integer> list = new ArrayList<>();
        int tmp = num;
        boolean flg = false;
        while (tmp != 0) {
            int n = tmp % 10;
            if (n != 0) {
                flg = true;
            }
            if (flg) {
                list.add(n);
            }
            tmp = tmp / 10;
        }

        int opposite = 0;
        for (Integer i : list) {
            opposite *= 10;
            opposite += i;
        }

        System.out.println(opposite + num);

    }

}
