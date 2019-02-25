package com.hy.jspider.animalworld.activity;

import com.google.gson.Gson;
import com.hy.jspider.Downloader;
import com.hy.jspider.animalworld.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author huangye
 */
public class AnimalActPipeline implements Pipeline {

    private static final String JSON_FILE_PATH = "/Users/huangye/AndroidStudioProject3/Hackathon/jspider/data/animalActs.json";
    public static final String DOWNLOAD_PATH = "/Users/huangye/AndroidStudioProject3/Hackathon/jspider/data/animalActsPic/";

    public static final String MY_WEB_URL = "http://111.230.232.189:8004/api/animalActsPic/";

    private AnimalActivityJson mAnimalAct;
    private Gson mGson = new Gson();

    public AnimalActPipeline() {
        mAnimalAct = new AnimalActivityJson();
        mAnimalAct.newAct = new ArrayList<>();
        mAnimalAct.oldAct = new ArrayList<>();
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<String> urls = resultItems.get("urls");
        List<String> titles = resultItems.get("titles");
        List<String> contents = resultItems.get("contents");
        List<String> dates = resultItems.get("dates");

        for (int i = 0; i < urls.size(); i++) {
            urls.set(i, "http://www.fon.org.cn/" + urls.get(i));
        }
        Downloader downloader = new Downloader();
        downloader.download(urls, DOWNLOAD_PATH);

        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            String[] splits = url.split("/");
            url = MY_WEB_URL + splits[splits.length - 1];

            AnimalActivity animalActivity = new AnimalActivity(url, titles.get(i), contents.get(i), dates.get(i));
            if (i < 6) {
                mAnimalAct.newAct.add(animalActivity);
            } else {
                mAnimalAct.oldAct.add(animalActivity);
            }
        }

        writeToFile(JSON_FILE_PATH, mGson.toJson(mAnimalAct));
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
