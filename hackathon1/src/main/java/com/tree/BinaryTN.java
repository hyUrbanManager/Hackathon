package com.tree;

/**
 * 二叉树节点
 *
 * @author hy 2017/10/29
 */
public class BinaryTN {

    public int data;

    public BinaryTN left;

    public BinaryTN right;

    public BinaryTN() {
        this(0, null, null);
    }

    public BinaryTN(int data, BinaryTN left, BinaryTN right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }
}
