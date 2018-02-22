/*
 * Copyright (c) 2017.
 * 版权为杰理所有。
 */

package com.hy.security;

import java.io.UnsupportedEncodingException;

/**
 * 格式化输出数据工具。
 *
 * @author hy 2017/10/9
 */
public class Formatter {

    public static final char tables[] = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
    };

    // 0 ~ 31 为不可见字符
    public static final int ILLEGAL_CHAR_INDEX_MIN = 0;
    public static final int ILLEGAL_CHAR_INDEX_MAX = 31;

    /**
     * Returns a string representation of the contents of the specified array.
     * The string representation consists of a list of the array's elements,
     * enclosed in square brackets (<tt>"[]"</tt>).  Adjacent elements
     * are separated by the characters <tt>", "</tt> (a comma followed
     * by a space).  Elements are converted to strings as by
     * <tt>String.valueOf(byte)</tt>.  Returns <tt>"null"</tt> if
     * <tt>a</tt> is <tt>null</tt>.
     *
     * @param a the array whose string representation to return
     * @return a string representation of <tt>a</tt>
     */
    public static String toHexStringArray(byte[] a) {
        if (a == null)
            return "null";
        int iMax = a.length - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            byte x = a[i];
            char h = tables[(x >> 4) & 0x0f];
            char l = tables[x & 0x0f];
            b.append(h);
            b.append(l);
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }

    /**
     * Returns a string representation of the contents of the specified array.
     * The string representation consists of a list of the array's elements,
     * enclosed in square brackets (<tt>"[]"</tt>).  Adjacent elements
     * are separated by the characters <tt>", "</tt> (a comma followed
     * by a space).  Elements are converted to strings as by
     * <tt>String.valueOf(byte)</tt>.  Returns <tt>"null"</tt> if
     * <tt>a</tt> is <tt>null</tt>.
     *
     * @param a the array whose string representation to return
     * @return a string representation of <tt>a</tt>
     */
    public static String toHexStringArray2(byte[] a) {
        if (a == null)
            return "null";
        int iMax = a.length - 1;
        if (iMax == -1)
            return "";

        StringBuilder b = new StringBuilder();
        for (int i = 0; ; i++) {
            byte x = a[i];
            char h = tables[(x >> 4) & 0x0f];
            char l = tables[x & 0x0f];
            b.append(h);
            b.append(l);
            if (i == iMax)
                return b.toString();
        }
    }
    /**
     * 转换字符。
     *
     * @param b
     * @param code 0->unicode,
     *             1->ascii。
     * @return string
     */
    public static String newString(byte[] b, int code) {
        String charset = code == 0 ? "UTF-16Le" : "US-ASCII";
        try {
            return new String(b, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "unknown";
    }

    /**
     * 2个字节按照大端方式组合。
     * 因为以下代码执行时在byte大于0x80时高位会出现反码，不能达到要求，封装一个方法来实现字节组合。
     * <p>
     * int totalItem = (data[0] << 24) | (data[1] << 16) | (data[2] << 8) | (data[3]);
     *
     * @param b1
     * @param b2
     * @return int
     */
    public static int combine2Bytes(byte b1, byte b2) {
        return combine4Bytes((byte) 0x00, (byte) 0x00, b1, b2);
    }

    /**
     * 4个字节按照大端方式组合。
     * 因为以下代码执行时在byte大于0x80时高位会出现反码，不能达到要求，封装一个方法来实现字节组合。
     * <p>
     * int totalItem = (data[0] << 24) | (data[1] << 16) | (data[2] << 8) | (data[3]);
     *
     * @param b1
     * @param b2
     * @param b3
     * @param b4
     * @return int
     */
    public static int combine4Bytes(byte b1, byte b2, byte b3, byte b4) {
        int d1 = (b1 << 24) & 0xff000000;
        int d2 = (b2 << 16) & 0x00ff0000;
        int d3 = (b3 << 8) & 0x0000ff00;
        int d4 = b4 & 0x000000ff;
        return d1 | d2 | d3 | d4;
    }

}
