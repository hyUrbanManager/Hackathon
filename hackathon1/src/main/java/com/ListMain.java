package com;

import com.list.Link;

import java.util.Arrays;

/**
 * Created by Administrator on 2018/2/14.
 *
 * @author hy 2018/2/14
 */
public class ListMain implements FastPrint {

    static int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public static void main(String[] args) {

        System.out.println("args: " + Arrays.toString(args));

        Link link = new Link(values[0]);
        for (int i = 1; i < values.length; i++) {
            link.add(values[i]);
        }
        System.out.println(link);

        link.remove(3);
        link.remove(5);
        link.remove(7);
        link.remove(8);
        System.out.println(link);

        link.reverse();
        System.out.println(link);
    }

}
