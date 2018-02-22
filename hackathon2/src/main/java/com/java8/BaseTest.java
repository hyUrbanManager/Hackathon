package com.java8;

import org.junit.Test;

/**
 * Created by Administrator on 2018/2/12.
 *
 * @author hy 2018/2/12
 */

public class BaseTest {

    @Test
    public void main() {
        Base[] bases = new ConA[10];
        bases[0] = new ConB();

        for (Base b : bases) {
            if (b != null) {
                b.action();
            }
        }

    }

    static class Base {
        void action() {
            System.out.println("Base");
        }
    }

    static class ConA extends Base {
        @Override
        void action() {
            System.out.println("ConA");
        }
    }

    static class ConB extends Base {
        @Override
        void action() {
            System.out.println("ConB");
        }
    }

}
