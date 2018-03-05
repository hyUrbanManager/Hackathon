package com.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * 稀疏矩阵某个点。
 *
 * @author hy 2018/3/5
 */
public class Point {

    public List<Point> nextPs;

    public int value;

    public Point() {
        this(0);
    }

    public Point(int value) {
        this.nextPs = new ArrayList<>();
        this.value = value;
    }

}
