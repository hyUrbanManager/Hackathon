package com.hy.jspider;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
 * 运行过程中是单例，但是为了外部获取方便，使用静态方法。
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

    private static int code;

    public static int getHttpCode() {
        return code;
    }

    private static String failUrl;

    public static String getFailUrl() {
        return failUrl;
    }

    private static Headers headers;

    public static Headers getHeaders() {
        return headers;
    }

    @Override
    public Page download(Request request, Task task) {
        if (task == null || task.getSite() == null) {
            throw new NullPointerException("task or site can not be null");
        }

        Light.programAcquiringHttp();

        Page page = Page.fail();

        try {
            okhttp3.Request r = new okhttp3.Request.Builder()
                    .url(request.getUrl())
                    .build();
            nowCall = client.newCall(r);
            Response response = nowCall.execute();
            page = handleResponse(request, request.getUrl(), response);

            code = response.code();

            if (code >= 300) {
                Logger.getLogger("OkHttpDownloader").info("downloading page fail: " +
                        response.code() + ", headers: \n" + response.headers().toString()
                        + "\nurl: " + request.getUrl());
                headers = response.headers().newBuilder().build();

                if (code == 429) {
                    Light.programRunning();
                } else {
                    // red light.
                    Light.programHttpError();
                }
                failUrl = request.getUrl();
            } else {
                Logger.getLogger("OkHttpDownloader").info("downloading page success {" + request.getUrl() + "}");
                Light.programRunning();
            }
        } catch (IOException e) {
            Logger.getLogger("OkHttpDownloader").error("download page error {" +
                    request.getUrl() + "}\n" + e.getMessage());
            page.setDownloadSuccess(false);

            // red light.
            Light.programHttpError();
            failUrl = request.getUrl();
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
