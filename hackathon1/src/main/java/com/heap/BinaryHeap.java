package com.heap;

/**
 * 二叉堆
 *
 * @author hy 2017/11/5
 */
public class BinaryHeap {

    // index 0 is null, from 1 to size - 1
    private int[] elementData;
    private int size;

    public static BinaryHeap buildHeap(int... rawData) {
        int capacity = 2;
        while (capacity < rawData.length + 1) {
            capacity *= 2;
        }
        BinaryHeap heap = new BinaryHeap(capacity);
        System.arraycopy(rawData, 0, heap.elementData, 1, rawData.length);
        heap.size = rawData.length;
        for (int i = heap.size / 2; i > 0; i--) {
            heap.percolateDown(i);
        }
        return heap;
    }

    /**
     * @param capacity 2的n次幂
     */
    public BinaryHeap(int capacity) {
        this.elementData = new int[capacity];
        this.size = 0;
    }

    /**
     * 插入数据，上滤法
     *
     * @param data
     */
    public void insert(int data) {
        if (size >= elementData.length - 1) {
            ensureCapacity(elementData.length * 2);
        }

        int hole = ++size;
        elementData[0] = data;
        while (elementData[hole / 2] > data) {
            elementData[hole] = elementData[hole / 2];
            hole /= 2;
        }
        elementData[hole] = data;
    }

    private void ensureCapacity(int newLength) {
        int[] newArray = new int[newLength];
        System.arraycopy(elementData, 0, newArray, 0, elementData.length);
        this.elementData = newArray;
    }

    /**
     * 最小的出队列，下滤法
     *
     * @return
     */
    public int deleteMin() {
        if (size == 0) {
            return -1;
        }

        int min = elementData[1];
        elementData[1] = elementData[size--];
        percolateDown(1);

        return min;
    }

    private void percolateDown(int hole) {
        int child;
        int tmp = elementData[hole];

        for (; hole * 2 <= size; hole = child) {
            child = hole * 2;
            if (child != size && elementData[child + 1] < elementData[child]) {
                child++;
            }
            if (elementData[child] < tmp) {
                elementData[hole] = elementData[child];
            } else {
                break;
            }
        }
        elementData[hole] = tmp;
    }

    /**
     * 队列大小
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    public boolean isCorrect() {
        int index = 1;
        while (index < size / 2) {
            int child = index * 2;
            if (elementData[child] != 0 && elementData[child] < elementData[index]) {
                return false;
            }
            if (elementData[child + 1] != 0 && elementData[child + 1] < elementData[index]) {
                return false;
            }
            index++;
        }
        return true;
    }

}
