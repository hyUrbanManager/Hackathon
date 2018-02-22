package com.hy.niukewang;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Administrator on 2018/1/29.
 *
 * @author hy 2018/1/29
 */
public class JinRiTouTiao {

    @Test
    public void main() throws FileNotFoundException {
        File file = new File("F:\\project\\Hackathon\\niukewang\\src" +
                "\\main\\java\\com\\hy\\niukewang\\JinRiTouTiao1.txt");
        Scanner scanner = new Scanner(file);
        int num = Integer.valueOf(scanner.nextLine());
        int[] fav = strToInts(scanner.nextLine());
        int groupNum = Integer.valueOf(scanner.nextLine());
        int[][] rang = new int[groupNum][3];
        for (int i = 0; i < groupNum; i++) {
            rang[i] = strToInts(scanner.nextLine());
        }

        // 输出
        for (int i = 0; i < groupNum; i++) {
            int startIndex = rang[i][0] - 1;
            int endIndex = rang[i][1] - 1;
            int value = rang[i][2];
            int count = 0;
            for (int j = startIndex; j <= endIndex; j++) {
                if (fav[j] == value) {
                    count++;
                }
            }
            System.out.println(count);
        }
    }

    public static int[] strToInts(String str) {
        String[] strs = str.split(" ");
        int[] ints = new int[strs.length];
        for (int i = 0; i < strs.length; i++) {
            ints[i] = Integer.valueOf(strs[i]);
        }
        return ints;
    }

}
