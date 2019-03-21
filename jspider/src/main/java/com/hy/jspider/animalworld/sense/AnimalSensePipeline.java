package com.hy.jspider.animalworld.sense;

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
public class AnimalSensePipeline implements Pipeline {

    private static final String JSON_FILE_PATH = "/Users/huangye/AndroidStudioProject3/Hackathon/jspider/data/animalSense.json";

    private AnimalSense mAnimalSense;
    private Gson mGson = new Gson();

    public AnimalSensePipeline() {
        mAnimalSense = new AnimalSense();
        mAnimalSense.sense = new ArrayList<>();
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String title = resultItems.get("title");
        String content = resultItems.get("content");

        AnimalSense.Sense sense = new AnimalSense.Sense(title, content);
        mAnimalSense.sense.add(sense);

        if (mAnimalSense.sense.size() >= 20) {
            writeToFile(JSON_FILE_PATH, mGson.toJson(mAnimalSense));
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
