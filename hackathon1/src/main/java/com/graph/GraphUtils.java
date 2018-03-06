package com.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/6.
 *
 * @author hy 2018/3/6
 */
public class GraphUtils {

    /**
     * 广度优先搜索。无权。
     *
     * @param graph
     * @param start
     * @return
     */
    public static List<VertexWrapper> search1(Graph graph, Vertex start) {
        List<VertexWrapper> openList = new ArrayList<>();
        VertexWrapper wrapper = new VertexWrapper(start);
        wrapper.weight = 0;
        openList.add(wrapper);
        List<Vertex> closeList = new ArrayList<>();
        closeList.add(start);
        List<VertexWrapper> result = new ArrayList<>();

        int weight = 1;
        while (openList.size() != 0) {
            VertexWrapper vw = openList.remove(0);
            result.add(vw);

            boolean hasNextFound = false;
            for (Edge e : vw.vertex.edges) {
                if (closeList.contains(e.desV)) {
                    continue;
                }
                hasNextFound = true;
                closeList.add(e.desV);
                VertexWrapper w = new VertexWrapper(e.desV);
                w.weight = weight;
                openList.add(w);
            }

            if (hasNextFound) {
                weight++;
            }
        }

        return result;
    }

    /**
     * 广度优先搜索。有权。fail.
     *
     * @param graph
     * @param start
     * @return
     */
    public static List<VertexWrapper> search2(Graph graph, Vertex start) {
        List<VertexWrapper> openList = new ArrayList<>();
        VertexWrapper wrapper = new VertexWrapper(start);
        wrapper.weight = 0;
        openList.add(wrapper);
        List<VertexWrapper> closeList = new ArrayList<>();
        closeList.add(wrapper);
        List<VertexWrapper> result = new ArrayList<>();

        int weight = 0;
        while (openList.size() != 0) {
            VertexWrapper vw = openList.remove(0);
            result.add(vw);

            for (Edge e : vw.vertex.edges) {
                VertexWrapper w = new VertexWrapper(e.desV);
                w.weight = weight + e.value;

                int index = closeList.indexOf(w);
                if (index == -1) {
                    closeList.add(w);
                } else {
                    VertexWrapper cw = closeList.get(index);
                    if (w.weight < cw.weight) {
                        closeList.remove(index);
                        closeList.add(w);
                    }
                }
                openList.add(w);
            }

        }

        return result;
    }

}
