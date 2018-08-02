package com.hy.niukewang;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangye on 2018/8/2.
 *
 * @author hy 2018/8/2
 */
public class CompareVersion {

    // 直接解析数字。
    @Test
    public void test() {
        System.out.println(compareVersion("11.1.0", "11.2.0"));
        System.out.println(compareVersion("11.3.1", "11.2.0"));
        System.out.println(compareVersion("11.2.0.1", "11.2.0"));
        System.out.println(compareVersion("11.2.0.0", "11.2.0"));
        System.out.println(compareVersion("11.1.0", "11.2.0"));
        System.out.println(compareVersion("11.1.0", "11.2.0"));
        System.out.println(compareVersion("11.1.0", "11.2.0"));
    }

    public int compareVersion(String version1, String version2) {
        List<List<Integer>> l1 = parseVersion(version1);
        List<List<Integer>> l2 = parseVersion(version2);
        int size = Math.min(l1.size(), l2.size());
        for (int i = 0; i < size; i++) {
            List<Integer> ll1 = l1.get(i);
            List<Integer> ll2 = l1.get(i);
            if (ll1.size() - ll2.size() < 0) {
                return 1;
            } else if (ll1.size() - ll2.size() > 0) {
                return -1;
            } else {
                for (int j = 0; j < ll1.size(); j++) {
                    int lll1 = ll1.get(j);
                    int lll2 = ll2.get(j);
                    if (lll1 > lll2) {
                        return 1;
                    } else if (lll1 < lll2) {
                        return -1;
                    }
                }
            }
        }
        if (l1.size() == l2.size()) {
            return 0;
        } else if (l1.size() > l2.size()) {
            return 1;
        } else {
            return -1;
        }
    }

    public List<List<Integer>> parseVersion(String version) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> l = new ArrayList<>();
        char[] cs = version.toCharArray();
        int index = 0;
        while (index < cs.length) {
            char c = cs[index];
            if (c == '.') {
                list.add(l);
                l = new ArrayList<>();
            } else {
                l.add(c - '0');
            }
            index++;
        }
        return list;
    }
}
