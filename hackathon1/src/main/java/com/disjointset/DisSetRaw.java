package com.disjointset;

/**
 * 不相交集
 *
 * @author hy 2017/11/17
 */
public class DisSetRaw {

    private int len;
    private int[] elements;

    public DisSetRaw(int len) {
        this.len = len;
        elements = new int[len];
        for (int i = 0; i < len; i++) {
            elements[i] = -1;
        }
    }

    /**
     * 求并，root2引用上root1。
     *
     * @param root1
     * @param root2
     */
    public void union(int root1, int root2) {
        root1 = find(root1);
        root2 = find(root2);
        if (root1 == root2) {
            return;
        }
        elements[root2] = root1;
    }

    /**
     * 查找树根。
     *
     * @param x
     * @return
     */
    public int find(int x) {
        if (elements[x] < 0) {
            return x;
        }
        return elements[x] = find(elements[x]);
    }

    public int size() {
        return len;
    }
}
