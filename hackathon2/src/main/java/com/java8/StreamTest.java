package com.java8;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2017/10/29.
 *
 * @author hy 2017/10/29
 */
public class StreamTest implements FastPrint {

    @Test
    public void test1() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Stream<String> list2 = list.stream()
                .filter(integer -> integer % 2 == 0)
                .map(a -> " " + a);

        list2.forEach(s -> p("hello " + s));
        list.forEach(s -> p("hello " + s));
    }


}
