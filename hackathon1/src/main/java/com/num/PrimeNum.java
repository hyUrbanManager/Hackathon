package com.num;

import com.FastPrint;

import org.junit.Test;

import java.lang.Math;
import java.util.Locale;

/**
 * Created by Administrator on 2018/1/16.
 *
 * @author hy 2018/1/16
 */
public class PrimeNum implements FastPrint {

    // 最后为 990727。
    final int MAX_PRIME_NUM = 1000 * 1000 * 10;

    @Test
    public void findPrimeNum1() {
        long t = startTimer();
        printPrime(2);
        printPrime(3);
        for (int i = 2; i <= MAX_PRIME_NUM; i++) {
            int sqrt = (int) Math.sqrt(i);
            for (int j = 2; j <= sqrt; j++) {
                if (i % j == 0) {
                    break;
                }
                if (j == sqrt) {
                    printPrime(i);
                }
            }
        }
        endTimer(t);
    }
    // 9925439 9941293 9957683 9974021 9990727

    /**
     * 筛选法。false为质数，true为合数。
     */
    @Test
    public void findPrimeNum2() {
        long t = startTimer();
        boolean[] array = new boolean[MAX_PRIME_NUM];
        for (int i = 2; i < array.length; i++) {
            if (array[i] == false) {
                for (int j = i + i; j < MAX_PRIME_NUM; j += i) {
                    array[j] = true;
                }
                printPrime(i);
            }
        }
        endTimer(t);
    }
    // 9925439 9941293 9957683 9974021 9990727

    /**
     * 线性筛选法。false为质数，true为合数。
     */
    @Test
    public void findPrimeNum3() {
        long t = startTimer();
        boolean[] array = new boolean[MAX_PRIME_NUM];
        for (int i = 2; i < array.length; i++) {
            if (array[i] == false) {
                for (int j = i + i; j < MAX_PRIME_NUM; j += i) {
                    array[j] = true;
                }
                printPrime(i);
            }
        }
        endTimer(t);
    }

    int cnt;

    private void printPrime(int primeNum) {
        cnt++;
        if (cnt >= 1000) {
            pp(String.format(Locale.CHINA, "%d ", primeNum));
            cnt = 0;
        }
    }

}
