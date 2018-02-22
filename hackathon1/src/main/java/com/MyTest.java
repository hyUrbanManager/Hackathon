package com;

import com.compress.GenerateCompressData;
import com.disjointset.DisSetRaw;
import com.heap.BinaryHeap;
import com.list.Queue;

import org.junit.Test;

import java.util.Random;

/**
 * Created by Administrator on 2017/10/29.
 *
 * @author hy 2017/10/29
 */
public class MyTest implements FastPrint {

    @Test
    public void listQueue1() {
        int[] data = new int[]{1, 3, 5, 7, 9};
        int index = 0;
        Queue<Integer> queue = new Queue<>();
        for (int i = 0; i < 100; i++) {
            queue.enqueue(data[0]);
            queue.enqueue(data[1]);
            queue.enqueue(data[2]);
            queue.enqueue(data[3]);
            queue.enqueue(data[4]);

            p("size: " + queue.size());
            p("dequeue: " + queue.dequeue());
            p("dequeue: " + queue.dequeue());
            p("dequeue: " + queue.dequeue());
            p("dequeue: " + queue.dequeue());
            p("size: " + queue.size());
            p();
        }
    }

    @Test
    public void testHeap() {
        BinaryHeap heap = new BinaryHeap(4);
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int data = random.nextInt(50) + 1;
            heap.insert(data);
        }
        boolean b = heap.isCorrect();


        int[] array = new int[heap.getSize()];
        int min;
        int index = 0;
        while ((min = heap.deleteMin()) != -1) {
            array[index++] = min;
        }

    }

    @Test
    public void testHeap2() {
        int[] testData = new int[16];
        Random random = new Random();
        for (int i = 0; i < testData.length; i++) {
            testData[i] = random.nextInt(50) + 1;
        }

        BinaryHeap heap = BinaryHeap.buildHeap(testData);
        boolean b = heap.isCorrect();

    }

    @Test
    public void test3() {
        int startNum = 1024 * 16;
        int sizes[] = new int[5];
        sizes[0] = startNum;
        for (int i = 1; i < sizes.length; i++) {
            sizes[i] = sizes[i - 1] * 8;
        }
        Random random = new Random();

        for (int s : sizes) {
            curHeapTime(s);
            System.gc();
        }
    }

    // 创建指定大小的堆。
    public void curHeapTime(int size) {
        Random random = new Random();
        int[] testData = new int[size];
        int max = size * 3;
        for (int i = 0; i < testData.length; i++) {
            testData[i] = random.nextInt(max) + 1;
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < 30; i++) {
            BinaryHeap heap = BinaryHeap.buildHeap(testData);
        }
        long time = System.currentTimeMillis() - start;
//        System.out.println(size + ": " + time + "  " + heap.isCorrect());
        System.out.println(size + ": " + time);
    }


    @Test
    public void testDisJointSet() {
        DisSetRaw disSetRaw = new DisSetRaw(20);
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int r1 = random.nextInt(20);
            int r2 = random.nextInt(20);
            disSetRaw.union(r1, r2);
            System.out.println("r1: " + r1 + " r2: " + r2);
        }

        for (int i = 0; i < disSetRaw.size(); i++) {
            System.out.print(disSetRaw.find(i) + "\t");
        }
    }

    @Test
    public void testGenerateCompress() {
//        GenerateCompressData.generateText("E://code_test_data//b.txt", 100000);
        GenerateCompressData.generateText2("E://code_test_data//c.txt", 100000);
    }

    @Test
    public void testCompress() {


    }

}
