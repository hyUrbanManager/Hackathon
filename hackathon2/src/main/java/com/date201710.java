package com;

import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017/10/14.
 *
 * @author hy 2017/10/14
 */

public class date201710 {

    /**
     * 链接：https://www.nowcoder.com/questionTerminal/376537f4609a49d296901db5139639ec
     * 一个整数总可以拆分为2的幂的和，例如：
     * 7=1+2+4
     * 7=1+2+2+2
     * 7=1+1+1+4
     * 7=1+1+1+2+2
     * 7=1+1+1+1+1+2
     * 7=1+1+1+1+1+1+1
     * 总共有六种不同的拆分方式。
     * 再比如：4可以拆分成：
     * 4 = 4，
     * 4 = 1 + 1 + 1 + 1，
     * 4 = 2 + 2，
     * 4 = 1 + 1 + 2。
     * 用f(n)表示n的不同拆分的种数，例如f(7)=6. 要求编写程序，读入n(不超过1000000)，
     * 输出f(n)%1000000000。
     * <p>
     * 需要知道递推公式。
     */
    public int f2n(int n) {
        int[] dp = new int[10001];
        for (int i = 1; i < n + 1; i++) {
            if (i == 1) {
                dp[i] = 1;
            } else if (i % 2 == 1) {
                dp[i] = dp[i - 1];
            } else {
                dp[i] = (dp[i - 1] + dp[i / 2]);
            }
        }
        return dp[n];
    }

    @Test
    public void main1() {
        p(" " + f2n(7));
        p(" " + f2n(4));
        p(" " + f2n(10));
        p(" " + f2n(15));
    }

    /**
     * 最优二叉树
     */
    public void tree1() {

    }

    /**
     * 递归
     */
    public int d1(int n) {
        if (n == 0) {
            return 0;
        }
        int r = d1(n - 1);
        return r + n;
    }

    @Test
    public void main2() {
        p(" " + d1(10));
        try {
            MessageDigest dm = MessageDigest.getInstance("MD5");
            dm.digest(new byte[]{1, 2, 3});
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    public void p(String msg) {
        System.out.println(msg);
    }
}
