package com;

import com.graph.Point;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 *
 * @author hy 2018/3/5
 */
public class GraphMain {

    @Test
    public void test() {
        Point[] ps = new Point[5];
        for (int i = 0; i < ps.length; i++) {
            ps[i] = new Point(i);
        }

        ps[0].nextPs.add(ps[1]);
        ps[1].nextPs.add(ps[2]);
        ps[2].nextPs.add(ps[3]);
        ps[3].nextPs.add(ps[4]);
        ps[4].nextPs.add(ps[1]);
        ps[0].nextPs.add(ps[2]);

        Point p = ps[0];
        List<Integer> list = new ArrayList<>();
        while (p != ps[4]) {
            list.add(p.value);
            p = p.nextPs.get(0);
        }

        for (Integer i : list) {
            System.out.print(i);
        }
    }

}
