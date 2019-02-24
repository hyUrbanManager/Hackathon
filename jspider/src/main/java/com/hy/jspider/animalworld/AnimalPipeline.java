package com.hy.jspider.animalworld;

import com.google.gson.Gson;
import com.hy.jspider.animalworld.news.Animal;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class AnimalPipeline implements Pipeline {

    private static final Logger LOG = Logger.getLogger(AnimalPipeline.class);
    private static final String JSON_FILE_PATH = "/Users/huangye/AndroidStudioProject3/Hackathon/jspider/data/science.json";
    private static final String PREFIX = "./upload/";

    private Gson mGson = new Gson();

    private List<String> mDownloadList = new ArrayList<>();

    private Science mScience = new Science();

    private Science.CategoriesBean mCategory;
    private int mID = 1;

    public AnimalPipeline() {
        mScience.categories = new ArrayList<>();
        Science.CategoriesBean categoriesBean = new Science.CategoriesBean();
        categoriesBean.id = 0;
        categoriesBean.title = "全部动物";
        mScience.categories.add(categoriesBean);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        int id = resultItems.get("id");
        String name = resultItems.get("title");
        List<String> imgUrl = resultItems.get("imgUrlList");
        List<String> nameList = resultItems.get("nameList");
        List<String> enNameList = resultItems.get("enNameList");
        List<String> descList = resultItems.get("descList");

        if (mCategory == null) {
            mCategory = new Science.CategoriesBean();
        }
        if (mCategory.id != id) {
            mScience.categories.add(mCategory);
            mCategory = new Science.CategoriesBean();
            mCategory.id = id;
            mCategory.title = name;
            mCategory.list = new ArrayList<>();
        }

        for (int i = 0; i < imgUrl.size(); i++) {
            String rawImgUrl = imgUrl.get(i);

            // 写入下载地址列表。
            mDownloadList.add(rawImgUrl);

            // 写入json。
            String[] ss = rawImgUrl.split("/");
            String coverImgUrl = PREFIX + ss[ss.length - 1];
            Animal animal = new Animal(mID++, coverImgUrl, nameList.get(i), enNameList.get(i), descList.get(i));
            mCategory.list.add(animal);

            LOG.info(mGson.toJson(animal));
        }

        if (resultItems.get("end") != null) {
            // 结束爬取，写入下载连接，json。
            StringBuilder sb = new StringBuilder();
            for (String url : mDownloadList) {
                sb.append(url).append('\n');
            }
            writeToFile(ImgDownloader.FILE_PATH, sb.toString());

            mScience.categories.add(mCategory);
            writeToFile(JSON_FILE_PATH, mGson.toJson(mScience));
        }
    }

    private void writeToFile(String fileName, String content) {
        File file = new File(fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(content.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
