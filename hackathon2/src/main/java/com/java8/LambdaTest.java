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

public class LambdaTest implements FastPrint {

    @Test
    public void test1() {
        Runnable runnable = () -> {
            p("hello! java8!");
            p("i will use java8");
        };
        runnable.run();
    }

    @Test
    public void test2() {
        Runnable runnable = () -> p("hello world!");
        runnable.run();
    }

    @Test
    public void test3() {
        List<String> list = Arrays.asList("hello ", "world !");
        list.forEach(System.out::print);
    }

    @Test
    public void test4() {
        String[] words = new String[]{"hello ", "world !"};
        Stream<String> stream = Stream.of(0, 1)
                .map(i -> words[i]);
        stream.forEach(this::p);
    }

    @Test
    public void test5() {
        Stream<String> stream = Stream.of("hello ", "no", "no", "world! ")
                .filter(str -> !str.equals("no"));
        stream.forEach(this::p);
    }

    @Test
    public void test6() {
        String words = Stream.of("w", "o", "r", "l", "d", "!")
                .reduce("hello ", (totalStr, str) -> totalStr + str);
        p(words);
    }
}
