package com;

import com.sort.NumMaterial;
import com.sort.SortUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/2/18.
 *
 * @author hy 2018/2/18
 */
public class SortMain {

    @Test
    public void debug() {
        main(new String[]{"123"});
    }

    public static void main(String[] args) {

        int[] arr = NumMaterial.unSortNum();

        SortUtils.merge(arr);

        System.out.println(NumMaterial.isSortCorrect(arr));
    }

    @Test
    public void quickSort() {

        int[] arr = NumMaterial.unSortNum();
        List<Integer> list = new ArrayList<>();
        for (int i : arr) {
            list.add(i);
        }

        SortUtils.quick(list);

        int[] result = new int[arr.length];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        System.out.println(NumMaterial.isSortCorrect(result));
    }

    @Test
    public void testMergeSort1() {
        int[] arr1 = {1, 4, 5, 8, 10, 17, 19};
        int[] arr2 = {4, 5, 7, 9, 10, 13, 15};

        int[] arr = SortUtils.mergeTwoSortedArr(arr1, arr2);

        System.out.println(Arrays.toString(arr));

    }

    @Test
    public void testMergeSort2() {
        int[] arr1 = {1, 4, 5, 8, 10, 17, 19};
        int[] arr2 = {4, 5, 7, 9, 10, 13, 15};

        int[] arr = new int[arr1.length + arr2.length];

        System.arraycopy(arr1, 0, arr, 0, arr1.length);
        System.arraycopy(arr2, 0, arr, arr1.length, arr2.length);

        int[] tmpArr = Arrays.copyOf(arr, arr.length);

        SortUtils.mergeArr(arr, tmpArr, 0, arr1.length, arr.length - 1);

        System.out.println(Arrays.toString(arr));

    }

}
