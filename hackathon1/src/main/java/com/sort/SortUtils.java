package com.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/18.
 *
 * @author hy 2018/2/18
 */
public class SortUtils {

    /**
     * 冒泡排序。
     *
     * @param arr
     */
    public static void bubble(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = arr.length - 1; j > i; j--) {
                if (arr[j - 1] > arr[j]) {
                    int tmp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = tmp;
                }
            }
        }
    }

    /**
     * 插入排序。
     *
     * @param arr
     */
    public static void insert(int[] arr) {
//        for (int i = 1; i < arr.length; i++) {
//            int index = i;
//            for (int j = i - 1; j >= 0; j--) {
//                if (arr[j] > arr[index]) {
//                    int tmp = arr[j];
//                    arr[j] = arr[index];
//                    arr[index] = tmp;
//                    index = j;
//                }
//            }
//        }

        for (int i = 1; i < arr.length; i++) {
            int tmp = arr[i];
            int j;
            for (j = i; j > 0 && tmp < arr[j - 1]; j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = tmp;
        }
    }

    /**
     * 。
     *
     * @param arr
     */
    public static void insert2(int[] arr) {
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

    /**
     * 希尔排序。
     *
     * @param arr
     */
    public static void shell(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                int tmp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > tmp; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = tmp;
            }
        }
    }

    /**
     * 堆排序。
     * [0 1 2 3 4 5 6 7 8 9 10]
     * <p>
     * 1-&gt;2,3 = 0-&gt;1,2
     * 2-&gt;4,5 = 1-&gt;3,4
     * 3-&gt;6,7 = 2-%gt;5,6
     *
     * @param arr
     */
    public static void heap(int[] arr) {
        // build heap.
        int[] heap = new int[arr.length + 1];
        for (int num : arr) {

        }
    }

    private static int leftChild(int i) {
        return (i + 1) * 2;
    }

    private static int rightChild(int i) {
        return (i + 1) * 2 + 1;
    }

    private void perDown() {


    }

    /**
     * 归并排序。
     *
     * @param arr
     */
    public static void merge(int[] arr) {
        int[] tmpArr = new int[arr.length];
        mergeSort(arr, tmpArr, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int[] tmpArr, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            mergeSort(arr, tmpArr, left, center);
            mergeSort(arr, tmpArr, center + 1, right);
            mergeArr(arr, tmpArr, left, center + 1, right);
        }
    }

    public static void mergeArr(int[] arr, int[] tmpArr, int left, int rightStart, int right) {
        int a = left, b = rightStart, c = left;
        while (a < rightStart && b < right + 1) {
            if (arr[a] > arr[b]) {
                tmpArr[c++] = arr[b++];
            } else {
                tmpArr[c++] = arr[a++];
            }
        }
        while (b < right + 1) {
            tmpArr[c++] = arr[b++];
        }
        while (a < rightStart) {
            tmpArr[c++] = arr[a++];
        }

        // copy to arr.
        System.arraycopy(tmpArr, left, arr, left, right - left + 1);
    }

    public static int[] mergeTwoSortedArr(int[] arr1, int[] arr2) {
        int a = 0, b = 0, c = 0;
        int[] arr = new int[arr1.length + arr2.length];

        while (a < arr1.length && b < arr2.length) {
            if (arr1[a] > arr2[b]) {
                arr[c++] = arr2[b++];
            } else {
                arr[c++] = arr1[a++];
            }
        }
        while (b < arr2.length) {
            arr[c++] = arr2[b++];
        }
        while (a < arr1.length) {
            arr[c++] = arr1[a++];
        }
        return arr;
    }

    /**
     * 快速排序。
     *
     * @param items
     */
    public static void quick(List<Integer> items) {
        if (items.size() == 0) {
            return;
        }

        List<Integer> small = new ArrayList<>();
        List<Integer> large = new ArrayList<>();
        List<Integer> same = new ArrayList<>();

        Integer chosen = items.get(items.size() / 2);

        for (Integer i : items) {
            if (i > chosen) {
                large.add(i);
            } else if (i.equals(chosen)) {
                same.add(i);
            } else {
                small.add(i);
            }
        }

        quick(large);
        quick(small);

        items.clear();
        items.addAll(small);
        items.addAll(same);
        items.addAll(large);
    }

}



















