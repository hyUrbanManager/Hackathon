package com.hy.jspider;

import com.hy.jspider.ess.XemhDownloader;

import org.apache.log4j.Logger;

import java.util.Date;

/**
 * 下载器。
 *
 * @author hy 2018/5/17
 */
public class MainDownload {

    public static void main(String[] args) {
        Logger.getLogger(Main.class).info("start download task.");
        Downloader downloader = new XemhDownloader();
        downloader.startDownload();

        // 增加退出钩子。
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            String exitTime = new Date().toString() + " exit jvm.";
            Logger.getLogger(Main.class).info(exitTime);
        }));
    }
}
