package com.tree;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 哈夫曼树
 *
 * @author hy 2017/10/29
 */
public class HuffmanTree {

    private static final Comparator<BinaryTN> comparator = new Comparator<BinaryTN>() {
        @Override
        public int compare(BinaryTN o1, BinaryTN o2) {
            return o1.data - o2.data;
        }
    };

    public BinaryTN root;

    public HuffmanTree() {
    }

    public HuffmanTree(BinaryTN root) {
        this.root = root;
    }

    /**
     * 从森林创建一个新的哈夫曼树
     *
     * @param forest
     * @return
     */
    public static HuffmanTree create(List<BinaryTN> forest) {
        while (forest.size() > 1) {
            Collections.sort(forest, comparator);
            BinaryTN n1 = forest.get(0);
            BinaryTN n2 = forest.get(1);
            BinaryTN cb = combine(n1, n2);
            forest.remove(0);
            forest.remove(1);
            forest.add(cb);
        }
        return new HuffmanTree(forest.get(0));
    }

    @SuppressWarnings("unchecked")
    private static BinaryTN combine(BinaryTN node1, BinaryTN node2) {
        int t = node1.data + node2.data;
        return new BinaryTN(t, node1, node2);
    }

}
