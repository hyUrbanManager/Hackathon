package com.hy.niukewang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * 小易准备去魔法王国采购魔法神器,购买魔法神器需要使用魔法币,但是小易现在一枚魔法币都没有,但是小易有两台魔法机器可以通过投入x(x可以为0)个魔法币产生更多的魔法币。
 * 魔法机器1:如果投入x个魔法币,魔法机器会将其变为2x+1个魔法币
 * 魔法机器2:如果投入x个魔法币,魔法机器会将其变为2x+2个魔法币
 * 小易采购魔法神器总共需要n个魔法币,所以小易只能通过两台魔法机器产生恰好n个魔法币,小易需要你帮他设计一个投入方案使他最后恰好拥有n个魔法币。
 * 输入描述:
 * 输入包括一行,包括一个正整数n(1 ≤ n ≤ 10^9),表示小易需要的魔法币数量。
 * <p>
 * <p>
 * 输出描述:
 * 输出一个字符串,每个字符表示该次小易选取投入的魔法机器。其中只包含字符'1'和'2'。
 * <p>
 * 输入例子1:
 * 10
 * <p>
 * 输出例子1:
 * 122
 *
 * @author hy 2018/3/5
 */
public class MagicCoin {

    public static void main(String[] args) {

        // 要产生10 ^ 9个魔法币。
        int num = 1000000000;

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        num = Integer.parseInt(input);

        int start = 0;

        // 尝试逆向搜索。

        List<Integer> list = new ArrayList<>();
        int tmp = num;
        while (tmp > 0) {
            if (tmp % 2 == 0) {
                tmp = (tmp - 2) / 2;
                list.add(2);
            } else {
                tmp = (tmp - 1) / 2;
                list.add(1);
            }
        }
        Collections.reverse(list);

        StringBuilder sb = new StringBuilder();
        for (Integer i : list) {
            sb.append(i);
        }

        System.out.println(sb);


        int result = 0;
        for (Integer i : list) {
            if (i == 1) {
                result = result * 2 + 1;
            } else if (i == 2) {
                result = result * 2 + 2;
            }
        }
        System.out.println(result == num);


    }

}















