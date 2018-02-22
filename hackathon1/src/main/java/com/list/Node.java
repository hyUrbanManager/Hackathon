package com.list;

/**
 * Created by Administrator on 2018/2/14.
 *
 * @author hy 2018/2/14
 */
public class Node {

    public int value;

    public Node next;

    public Node() {
        this(0, null);
    }

    public Node(int value, Node next) {
        this.value = value;
        this.next = next;
    }
}
