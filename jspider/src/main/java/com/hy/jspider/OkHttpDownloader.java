package com.hy.jspider;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.selector.PlainText;

/**
 * 使用Okhttp。
 *
 * @author hy 2018/6/4
 */
public class OkHttpDownloader implements us.codecraft.webmagic.downloader.Downloader {

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build();

    private Call nowCall;

    public static boolean isFail = false;
    public static String failUrl;

    @Override
    public Page download(Request request, Task task) {
        if (task == null || task.getSite() == null) {
            throw new NullPointerException("task or site can not be null");
        }

        Main.light(4);

        Page page = Page.fail();
        boolean isSuccess = false;
        isFail = false;

        try {
            okhttp3.Request r = new okhttp3.Request.Builder()
                    .url(request.getUrl())
//                    .headers(new Headers()request.getHeaders())
                    .build();
            nowCall = client.newCall(r);
            Response response = nowCall.execute();
            page = handleResponse(request, request.getUrl(), response);

            if (response.code() != 200) {
                // red light.
                Main.light(1);
                isFail = true;
                failUrl = request.getUrl();
            }

            isSuccess = true;
            Logger.getLogger("OkHttpDownloader").info("downloading page success {}" + request.getUrl());
        } catch (IOException e) {
            Logger.getLogger("OkHttpDownloader").warning("download page {} error" + request.getUrl() + " " + e.getMessage());

            // red light.
            Main.light(1);
            isFail = true;
            failUrl = request.getUrl();
        }

        if (isSuccess) {
            Main.light(5);
        }

        return page;
    }

    @Override
    public void setThread(int i) {

    }

    /**
     * 装填请求。
     *
     * @param request
     * @param url
     * @param response
     * @return
     */
    private Page handleResponse(Request request, String url, Response response) {
        Page page = new Page();

        try {
            ResponseBody body = response.body();
            String text = body.string();
            page.setCharset("okhttp charset");
            page.setRawText(text);
            page.setBytes(text.getBytes());

            page.setUrl(new PlainText(url));
            page.setRequest(request);
            page.setStatusCode(response.code());
            page.setDownloadSuccess(true);

            Headers headers = response.headers();
            page.setHeaders(headers.toMultimap());
        } catch (Exception e) {
            e.printStackTrace();
            page.setDownloadSuccess(false);
        }

        return page;
    }
}
