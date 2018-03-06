package com.graph;

/**
 * Created by Administrator on 2018/3/6.
 *
 * @author hy 2018/3/6
 */
public class Edge {

    public int value;

    public Vertex desV;

    public Edge() {
        this(0, null);
    }

    public Edge(int value, Vertex desV) {
        this.value = value;
        this.desV = desV;
    }
}
