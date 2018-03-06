package com.graph;

/**
 * 顶点装饰器，以增加新的属性。
 *
 * @author hy 2018/3/6
 */
public class VertexWrapper {

    // 起点到该点的权值。
    public int weight;

    public Vertex vertex;

    public VertexWrapper(Vertex vertex) {
        this.weight = 0;
        this.vertex = vertex;
    }

    @Override
    public boolean equals(Object obj) {
        VertexWrapper w = (VertexWrapper) obj;
        return vertex.equals(w.vertex);
    }

    @Override
    public int hashCode() {
        return vertex.hashCode();
    }
}
