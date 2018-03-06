package com;

import com.graph.Edge;
import com.graph.Graph;
import com.graph.GraphUtils;
import com.graph.Vertex;
import com.graph.VertexWrapper;

import org.junit.Test;

import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 *
 * @author hy 2018/3/5
 */
public class GraphMain {

    /**
     * 构造一个图。// p365 9.8
     *
     * @return
     */
    public static Graph generateGraph() {
        Graph graph = new Graph();

        Vertex[] vs = new Vertex[8];
        graph.vs.add(new Vertex(-1));
        for (int i = 1; i < vs.length; i++) {
            vs[i] = new Vertex(i);
            graph.vs.add(vs[i]);
        }

        vs[3].edges.add(new Edge(4, vs[1]));
        vs[3].edges.add(new Edge(5, vs[6]));
        vs[4].edges.add(new Edge(2, vs[3]));
        vs[1].edges.add(new Edge(1, vs[4]));
        vs[4].edges.add(new Edge(8, vs[6]));
        vs[1].edges.add(new Edge(2, vs[2]));
        vs[7].edges.add(new Edge(1, vs[6]));
        vs[2].edges.add(new Edge(3, vs[4]));
        vs[4].edges.add(new Edge(4, vs[7]));
        vs[4].edges.add(new Edge(2, vs[5]));
        vs[2].edges.add(new Edge(10, vs[5]));
        vs[5].edges.add(new Edge(6, vs[7]));

        return graph;
    }

    @Test
    public void test() {
        Graph graph = generateGraph();

        List<VertexWrapper> list = GraphUtils.search1(graph, graph.vs.get(3));

    }

}















