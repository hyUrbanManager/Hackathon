package com.hy.self;

/**
 * 自己原创出题
 * <p>
 * 一个数组的子序列当满足以下2个条件时：
 * 1、长度为奇数。
 * 2、从中间位数开始，往左右数字依次递增。
 * 称为“翼”。统计给定一个长度大于3且相邻数字不等的数组，有多少个“翼”
 * 例如：
 * [3,2,3,1,2,3,2,1,2,3]
 * 4
 * <p>
 * 记录大小变化，O(n)。O(n^2)算法减分。
 * [3,2,3,1,2,3,2,1,2,3]
 *
 * @author huangye
 */
public class WingArray {

    public static void main(String[] args) {
        int[] array = {3, 2, 3, 1, 2, 3, 2, 1, 2, 3};
        System.out.println(getWing(array));
    }

    public static int getWing(int[] array) {
        int sum = 0;

        boolean lastIsUp = array[1] > array[0];
        int downCount = lastIsUp ? 0 : 1;
        int upCount = lastIsUp ? 1 : 0;

        for (int i = 1; i < array.length - 1; i++) {
            boolean isUp = array[i + 1] > array[i];
            if (isUp) {
                if (!lastIsUp) {
                    // 变换为上升趋势，上升统计重新开始。
                    upCount = 1;
                } else {
                    // 继续为上升趋势，上升统计增加。
                    upCount++;
                }
                // 如果有前面匹配足够的下降数，翼统计++
                if (downCount >= upCount) {
                    sum++;
                }
            } else {
                if (lastIsUp) {
                    // 变换为下降趋势，下降统计重新开始。
                    downCount = 1;
                } else {
                    // 继续为下降趋势，下降统计增加。
                    downCount++;
                }
            }

            lastIsUp = isUp;
        }

        return sum;
    }
}
