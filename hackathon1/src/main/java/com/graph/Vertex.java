package com.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/6.
 *
 * @author hy 2018/3/6
 */
public class Vertex {

    public int seq;
    public int value;

    public List<Edge> edges;

    public Vertex(int seq) {
        this(seq, 0);
    }

    public Vertex(int seq, int value) {
        this.seq = seq;
        this.value = value;
        this.edges = new ArrayList<>();
    }
}
