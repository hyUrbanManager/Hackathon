package com.list;

import java.util.Arrays;

/**
 * 1、定义满队列，空队列，1元素时的front back情况
 * 2、扩容处理
 *
 * @author hy 2017/10/29
 */
public class Queue<E> {

    private Object[] elementData;

    // 数组长度，最大储存数量为数组长度减1
    private int arraySize = 16;

    // 指向当前头元素的位置
    private int front;

    // 指向当前尾的后一个的位置
    private int back;

    public Queue() {
        elementData = new Object[arraySize];
        front = 0;
        back = 0;
    }

    public void enqueue(E e) {
        int b = back + 1;
        if (b >= arraySize) {
            b = 0;
        }
        if (b == front) {
            ensureCapacity(arraySize * 2);
            elementData[back++] = e; // 扩容两倍，不必考虑回归
            return;
        }
        elementData[back] = e;
        back = b;
    }

    public E dequeue() {
        if (front == back) {
            return null;
        }
        E e = elementData(front);
        front++;
        if (front >= arraySize) {
            front = 0;
        }
        return e;
    }

    public int size() {
        int size = back - front;
        if (size < 0) {
            size += arraySize;
        }
        return size;
    }

    @SuppressWarnings("unchecked")
    private E elementData(int index) {
        return (E) elementData[index];
    }

    private static final int QUEUE_MAX_LEN = Integer.MAX_VALUE - 8;

    private boolean ensureCapacity(int newLength) {
        if (newLength > QUEUE_MAX_LEN) {
            return false;
        }
        int newArraySize = arraySize;
        if (newLength > newArraySize) {
            while (newArraySize < newLength) {
                newArraySize *= 2;
                if (newArraySize > QUEUE_MAX_LEN / 2) {
                    return false;
                }
            }
            Object[] newArray = grow(newArraySize);
            reQueue(newArray);
            arraySize = newArraySize;
        }
        return true;
    }

    private Object[] grow(int arraySize) {
        return Arrays.copyOf(elementData, arraySize);
    }

    private void reQueue(Object[] newArray) {
        int index = 0;
        E e;
        while ((e = dequeue()) != null) {
            newArray[index] = e;
            index++;
        }
        elementData = newArray;
        front = 0;
        back = index;
    }
}
