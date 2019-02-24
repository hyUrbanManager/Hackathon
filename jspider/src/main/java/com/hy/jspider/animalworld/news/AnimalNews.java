package com.hy.jspider.animalworld.news;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnimalNews {

    @SerializedName("animalNews")
    public List<AnimalNewsBean> animalNews;

    public static class AnimalNewsBean {

        /**
         * title :
         * time :
         * content :
         */

        @SerializedName("title")
        public String title;
        @SerializedName("time")
        public String time;
        @SerializedName("content")
        public String content;
    }
}
