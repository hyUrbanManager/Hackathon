package com.disjointset;

/**
 * 不相交集泛型
 *
 * @param <D> 数据类型
 * @param <S> 所属集合类型
 * @author hy 2017/11/18
 */
@Deprecated
public class DisSet<D, S> {

    private int len;
    private int[] data;
    private Object[] set;

    public DisSet(int len) {
        this.len = len;
        data = new int[len];
        set = new Object[len];

    }

    public void unionData(D d1, D d2) {

    }

    public void unionSet(S s1, S s2) {

    }

    public S find(D d) {
        return null;
    }

}
