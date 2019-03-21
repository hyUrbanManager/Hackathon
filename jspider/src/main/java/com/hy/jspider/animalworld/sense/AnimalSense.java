package com.hy.jspider.animalworld.sense;

import java.util.List;

public class AnimalSense {

    List<Sense> sense;

    public static class Sense {

        String title;

        String content;

        public Sense(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }
}
