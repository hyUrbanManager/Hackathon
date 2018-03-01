package com.hy.parser.okhttp;

import com.hy.parser.ValueUtil;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/3/1.
 *
 * @author hy 2018/3/1
 */
public class Main {

    @Test
    public void test() throws IOException {
        // 测试断点传输。
        String url = "http://fdfs.xmcdn.com/group22/M05/CA/18/wKgJLlhTRP3wABTrAA4pm-9jC50601.mp3";

        final OkHttpClient client = new OkHttpClient.Builder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "Main.java")
                .addHeader("Range", "bytes=100-200")
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            byte[] bytes = body.bytes();
            String contentLen = response.header("Content-Length", "0");
            System.out.println(ValueUtil.byte2HexStr(bytes, bytes.length));
            System.out.println(contentLen);
        } else {
            System.out.println(response.message());
        }

    }

    @Test
    public void test2() throws IOException {
        // 测试https连接。
        String url = "https://api.github.com";

        final OkHttpClient client = new OkHttpClient.Builder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "Main.java")
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            String s = body.string();
            String contentLen = response.header("Content-Length", "0");
            System.out.println(s);
            System.out.println(contentLen);
        } else {
            System.out.println(response.message());
        }

    }

    @Test
    public void test3() throws IOException {
        // 测试流。
        String url = "http://fdfs.xmcdn.com/group22/M05/CA/18/wKgJLlhTRP3wABTrAA4pm-9jC50601.mp3";

        final OkHttpClient client = new OkHttpClient.Builder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "Main.java")
                .addHeader("Range", "bytes=101-200000")
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            InputStream is = body.byteStream();
            byte[] bytes = new byte[1024 * 1];
            int totalLen = 0;
            int len;
            while ((len = is.read(bytes)) != -1) {
                System.out.println(len);
                totalLen += len;
            }
            System.out.println("total: " + totalLen);
            is.close();
        } else if (response.isRedirect()) {
            System.out.println(response.header("Location", "null location"));
        } else {
            System.out.println(response.message());
        }

    }
}
