package com.hy.jspider.animalworld;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author huangye
 */
public class Science {

    @SerializedName("categories")
    public List<CategoriesBean> categories;

    public static class CategoriesBean {

        /**
         * id : 0
         * title : 全部动物
         * list : [{"id":0,"title":"我是哺乳动物","img_url":"http://192.168.100.18:8004/api/upload/01.jpg","content":"啦啦啦啦啦啦你们好"},{"id":1,"title":"我是小搜搜","img_url":"http://192.168.100.18:8004/api/upload/02.jpg","content":"啦啦干干干干干干你们好"},{"id":2,"title":"我是小刚刚","img_url":"http://192.168.100.18:8004/api/upload/03.jpg","content":"啦啦就将计就计你们好"},{"id":3,"title":"我是小天天","img_url":"http://192.168.100.18:8004/api/upload/04.jpg","content":"顶顶顶顶顶顶顶顶顶顶啦你们好"}]
         */

        @SerializedName("id")
        public int id;
        @SerializedName("title")
        public String title;
        @SerializedName("list")
        public List<Animal> list;

    }
}
