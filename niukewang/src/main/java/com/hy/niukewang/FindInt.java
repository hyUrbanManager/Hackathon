package com.hy.niukewang;

import org.junit.Test;

/**
 * Created by huangye on 2018/8/2.
 *
 * @author hy 2018/8/2
 */
public class FindInt {

    @Test
    public void test() {
        System.out.println(findInt(new int[]{-1,1,8,4,5,2,6,2}));
        System.out.println(findInt(new int[]{-3,1,9,4,5,3,7,3}));
        System.out.println(findInt(new int[]{2,2,8,4,5,3,6,4}));
        System.out.println(findInt(new int[]{1,3,8,4,5,2,6,2}));
        System.out.println(findInt(new int[]{1,3,9,4,5,7,6,2}));
    }

    /**
     * 2、有一个无序的整数数组，找出其中没出现的最小正整数
     * 要求：O(n)时间复杂度、O(1)空间复杂度
     * 举例：给定数组[-1, 1, 8, 4, 5, 2, 6, 2]，返回3
     *
     * @param arr
     * @return
     */
    public int findInt(int[] arr) {
        int left = 0; //l表示已经从1到L已经出现（左边界），l的初值为0。
        int right = arr.length; //如果一个数字过大（不合法），就会被扔掉，用r表示这个右边界，r初始值为arr长度。

        while (left < right) {
            if (arr[left] == left + 1) {//位置合理
                left++;
            } else if (arr[left] > right || arr[left] <= left || arr[arr[left] - 1] == arr[left]) {
                //不合法的数字，三种情况:1.数字 <= left 2.数字 > right 3.要交换的位置和当前位置的数字相等
                arr[left] = arr[--right];
            } else {
                int temp = arr[left];
                arr[left] = arr[arr[left] - 1];
                arr[temp - 1] = temp;
            }
        }
        return left + 1;
    }
}
