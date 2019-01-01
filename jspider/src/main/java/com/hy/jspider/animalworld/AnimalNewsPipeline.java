package com.hy.jspider.animalworld;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author huangye
 */
public class AnimalNewsPipeline implements Pipeline {

    private static final String JSON_FILE_PATH = "/Users/huangye/AndroidStudioProject3/Hackathon/jspider/data/animalNews.json";

    private AnimalNews mAnimalNews;
    private Gson mGson = new Gson();

    public AnimalNewsPipeline() {
        mAnimalNews = new AnimalNews();
        mAnimalNews.animalNews = new ArrayList<>();
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        AnimalNews.AnimalNewsBean newsBean = resultItems.get("news");
        if (newsBean != null) {
            mAnimalNews.animalNews.add(newsBean);
        }

        if (mAnimalNews.animalNews.size() == 20) {
            writeToFile(JSON_FILE_PATH, mGson.toJson(mAnimalNews));
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
