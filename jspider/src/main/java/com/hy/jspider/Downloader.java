package com.hy.jspider;

import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;

/**
 * 下载器，从mysql提取链接下载。
 *
 * @author hy 2018/5/14
 */
public class Downloader {

    public void startDownload() {
    }

    /**
     * url list下载。
     *
     * @param urlList
     */
    public void download(List<String> urlList, String downloadPath) {
        Logger.getLogger(Downloader.class).info("download size: " + urlList.size());
        int num = 1;
        for (int i = 0; i < urlList.size(); i++) {
            String url = urlList.get(i);
            String command = "curl -O " + url;
            System.out.println("command: " + command);
            try {
                Process process = Runtime.getRuntime().exec(command, null, new File(downloadPath));
                int r = process.waitFor();
                if (r != 0) {
                    Logger.getLogger(Downloader.class).info("download " + url +
                            " fail, reason is " + r + ".");
                } else {
                    Logger.getLogger(Downloader.class).info("download " + url +
                            " success. has done: " + num++);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * url list下载。
     *
     * @param urlList
     * @param fileNames
     */
    public void download(List<String> urlList, List<String> fileNames) {
        if (fileNames.size() != urlList.size()) {
            Logger.getLogger(Downloader.class).info("param erro. size is: " + urlList.size() +
                    ", " + fileNames.size());
            return;
        }
        Logger.getLogger(Downloader.class).info("download size: " + urlList.size());
        int num = 1;
        for (int i = 0; i < urlList.size(); i++) {
            String url = urlList.get(i);
            String fileName = fileNames.get(i);
            String command = "curl -o " + getDownLoadDir() + "/" + fileName + " " + url;
            System.out.println("command: " + command);
            try {
                Process process = Runtime.getRuntime().exec(command);
                int r = process.waitFor();
                if (r != 0) {
                    Logger.getLogger(Downloader.class).info("download " + url +
                            " fail, reason is " + r + ".");
                } else {
                    Logger.getLogger(Downloader.class).info("download " + url +
                            " success. has done: " + num++);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 返回下载到的目录，可重写。
     *
     * @return
     */
    public String getDownLoadDir() {
        return "/mnt/sd_card";
    }

}
