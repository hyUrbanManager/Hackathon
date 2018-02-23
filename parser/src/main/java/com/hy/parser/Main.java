package com.hy.parser;

import com.hy.parser.html.HtmlParseException;
import com.hy.parser.html.HtmlRequest;
import com.hy.parser.html.HtmlResponse;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Main {

    @Test
    public void test1() throws HtmlParseException, IOException {
        String raw = "GET / HTTP/1.1\r\n" +
                "Cache-Control: no-cache\r\n" +
                "Connection: keep-alive\r\n" +
                "Host: 139.199.170.98\r\n" +
                "User-Agent: Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.2; WOW64; Trident/7.0; .NET4.0E; .NET4.0C; .NET CLR 3.5.30729; .NET CLR 2.0.50727; .NET CLR 3.0.30729)\r\n" +
                "\r\n";

        HtmlRequest request = HtmlRequest.parseString(raw);

        Socket socket = new Socket("139.199.170.98", 80);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        // 修改Request
        request.Range = "bytes=100~300";

        String writeThing = request.toString();
        os.write(writeThing.getBytes());

        byte[] bytes = new byte[1024 * 10];
        int len;
        StringBuilder sb = new StringBuilder();

        HtmlResponse response = null;

        while ((len = is.read(bytes)) != -1) {
            String pp = new String(bytes, 0, len);
            sb.append(pp);

            if (pp.contains("\r\n\r\n")) {
                response = HtmlResponse.parseString(pp);
            }
        }

        String s = sb.toString();
        System.out.println(s);

        assert response != null;
        String k = response.toString();

    }


    @Test
    public void test2() throws HtmlParseException, IOException {
        String raw = "GET /video1.wmv HTTP/1.1\r\n" +
                "Cache-Control: no-cache\r\n" +
                "Connection: keep-alive\r\n" +
                "Host: 139.199.170.98\r\n" +
                "User-Agent: Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.2; WOW64; Trident/7.0; .NET4.0E; .NET4.0C; .NET CLR 3.5.30729; .NET CLR 2.0.50727; .NET CLR 3.0.30729)\r\n" +
                "\r\n";

        HtmlRequest request = HtmlRequest.parseString(raw);

        Socket socket = new Socket("139.199.170.98", 80);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        // 修改Request
        request.Range = "bytes=256-300";

        String writeThing = request.toString();
        os.write(writeThing.getBytes());

        byte[] bytes = new byte[1024 * 10];
        int len;
        StringBuilder sb = new StringBuilder();

        HtmlResponse response = null;

        if ((len = is.read(bytes)) != -1) {
            String pp = new String(bytes, 0, len);

            if (pp.contains("\r\n\r\n")) {
                response = HtmlResponse.parseString(pp);

                int index = pp.indexOf("\r\n\r\n");
                index += 4;

                byte[] bs = new byte[1024 * 10];
                System.arraycopy(bytes, index, bs, 0, len - index - 10);

                System.out.println(ValueUtil.byte2HexStr(bs, len - index - 10));
            }

        }


        assert response != null;
        String k = response.toString();

        System.out.println(response.ContentLength);

        is.close();
        os.close();
        socket.close();

    }

}
