package com.sort;

/**
 * Created by Administrator on 2018/2/18.
 *
 * @author hy 2018/2/18
 */
public class SortUtils {

    public static void bubble(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    int tmp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = tmp;
                }
            }
        }
    }

    public static void insert(int[] arr) {



    }

}
