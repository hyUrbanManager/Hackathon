package com.hy.jspider.animalworld;

import com.hy.jspider.Downloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huangye
 */
public class ImgDownloader extends Downloader {

    public static final String FILE_PATH = "/Users/huangye/AndroidStudioProject3/Hackathon/jspider/log/download.txt";
    public static final String DOWNLOAD_PATH = "/Users/huangye/AndroidStudioProject3/Hackathon/jspider/data/animalPic";

    public static void main(String[] args) {
        List<String> downloadList = new ArrayList<>();

        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(new File(FILE_PATH));
            br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                downloadList.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(downloadList.size());

        ImgDownloader downloader = new ImgDownloader();
        downloader.download(downloadList, DOWNLOAD_PATH);
    }

    @Override
    public void startDownload() {
    }

    @Override
    public String getDownLoadDir() {
        return DOWNLOAD_PATH;
    }
}
