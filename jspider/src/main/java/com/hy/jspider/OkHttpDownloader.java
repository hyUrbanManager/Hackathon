package com.hy.jspider;

import org.apache.http.Header;
import org.apache.http.util.EntityUtils;

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
import us.codecraft.webmagic.utils.HttpClientUtils;

/**
 * 使用Okhttp。
 *
 * @author hy 2018/6/4
 */
public class OkHttpDownloader implements us.codecraft.webmagic.downloader.Downloader {

    private Logger logger;

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build();

    private Call nowCall;

    public OkHttpDownloader() {
        logger = Logger.getLogger("OkHttpDownloader");
    }

    @Override
    public Page download(Request request, Task task) {
        if (task == null || task.getSite() == null) {
            throw new NullPointerException("task or site can not be null");
        }
        Page page = Page.fail();

        try {
            okhttp3.Request r = new okhttp3.Request.Builder()
                    .url(request.getUrl())
//                    .headers(new Headers()request.getHeaders())
                    .build();
            nowCall = client.newCall(r);
            Response response = nowCall.execute();
            page = handleResponse(request, request.getUrl(), response);

            logger.info("downloading page success {}" + request.getUrl());
        } catch (IOException e) {
            logger.warning("download page {} error" + request.getUrl() + " " + e.getMessage());
        }
        return page;
    }

    @Override
    public void setThread(int i) {

    }

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
