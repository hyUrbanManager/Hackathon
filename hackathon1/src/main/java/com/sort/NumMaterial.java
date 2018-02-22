package com.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Administrator on 2018/2/18.
 *
 * @author hy 2018/2/18
 */
public class NumMaterial {

    static int[] unSortNum;
    static int[] sortNum;
    static final int count = 20;
    static final int max = 100;
    static final int mix = 1;

    public static int[] unSortNum() {
        unSortNum = new int[count];
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            unSortNum[i] = random.nextInt(max - mix) + mix;
        }

        System.out.println("material:" + Arrays.toString(unSortNum));

        return unSortNum;
    }

    public static boolean isSortCorrect(int[] result) {
        if (unSortNum == null) {
            throw new NullPointerException("unSortNum is null");
        }
        sortNum = Arrays.copyOf(unSortNum, count);
        Arrays.sort(sortNum);

        System.out.println("correct: " + Arrays.toString(sortNum));
        System.out.println("your re: " + Arrays.toString(result));

        return Arrays.equals(sortNum, result);
    }

}
