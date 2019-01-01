package com.hy.jspider.animalworld;

import com.google.gson.annotations.SerializedName;

/**
 * @author huangye
 */
public class Animal {

    Animal(int id, String imgUrl, String name, String enName, String desc) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.name = name;
        this.enName = enName;
        this.desc = desc;
    }

    /**
     * id:
     * imgUrl :
     * name :
     * enName :
     * desc :
     */
    @SerializedName("id")
    public int id;
    @SerializedName("imgUrl")
    public String imgUrl;
    @SerializedName("name")
    public String name;
    @SerializedName("enName")
    public String enName;
    @SerializedName("desc")
    public String desc;
}
