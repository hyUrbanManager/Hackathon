package com.list;

/**
 * Created by Administrator on 2018/2/14.
 *
 * @author hy 2018/2/14
 */
public class Link {

    Node head;
    int size;

    public Link(int value) {
        this.head = new Node(value, null);
        size = 1;
    }

    /**
     * @param value
     */
    public void add(int value) {
        Node n = head;
        while (n.next != null) {
            n = n.next;
        }
        n.next = new Node(value, null);
        size++;
    }

    /**
     * @param value
     */
    public boolean remove(int value) {
        Node n = head;
        Node pre = n;
        if (n.value == value) {
            head = head.next;
            size--;
            return true;
        }
        boolean flag = false;
        while (n != null) {
            if (n.value == value) {
                pre.next = n.next;
                size--;
                flag = true;
            }
            pre = n;
            n = n.next;
        }
        return flag;
    }

    public int size() {
        return size;
    }

    /**
     * 反转链表。
     * 使用尾遍历更有效率。
     */
    @Deprecated
    public void reverse() {
        Node n = head;
        Node pre = n;
        Node next = n.next;


        n.next = pre;


    }

    @Override
    public String toString() {
        Node n = head;
        StringBuilder sb = new StringBuilder();
        while (n != null) {
            sb.append(n.value);
            sb.append(' ');
            n = n.next;
        }
        return sb.toString();
    }
}
