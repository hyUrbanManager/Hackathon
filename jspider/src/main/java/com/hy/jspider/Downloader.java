package com.hy.jspider;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * 下载器，从mysql提取链接下载。
 *
 * @author hy 2018/5/14
 */
public abstract class Downloader {

    public abstract void startDownload();

    /**
     * url list下载。
     *
     * @param urlList
     */
    public void download(List<String> urlList) {
        int num = 0;
        for (String url : urlList) {
            String[] ss = url.split("/");
            String fileName = ss[ss.length - 1];
            String command = "curl -o " + getDownLoadDir() + "/" + fileName + " " + url;
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
