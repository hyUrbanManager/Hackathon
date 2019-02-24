package com.hy.jspider.animalworld.movie;

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
public class AnimalMoviePipeline implements Pipeline {

    private static final String JSON_FILE_PATH = "/Users/huangye/AndroidStudioProject3/Hackathon/jspider/data/animalMovies.json";
    public static final String DOWNLOAD_PATH = "/Users/huangye/AndroidStudioProject3/Hackathon/jspider/data/animalMoviesPic/";

    public static final String MY_WEB_URL = "http://111.230.232.189:8004/api/animalMoviesPic/";

    private AnimalMovie mAnimalMovie;
    private Gson mGson = new Gson();

    public AnimalMoviePipeline() {
        mAnimalMovie = new AnimalMovie();
        mAnimalMovie.movies = new ArrayList<>();
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<String> names = resultItems.get("names");
        List<String> urls = resultItems.get("urls");

        Downloader downloader = new Downloader();
        downloader.download(urls, DOWNLOAD_PATH);

        for (int i = 0; i < names.size(); i++) {
            String url = urls.get(i);
            String[] splits = url.split("/");
            url = MY_WEB_URL + splits[splits.length - 1];

            Data data = new Data(names.get(i), url);
            mAnimalMovie.movies.add(data);
        }

        writeToFile(JSON_FILE_PATH, mGson.toJson(mAnimalMovie));
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
