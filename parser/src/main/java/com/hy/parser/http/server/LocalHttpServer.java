package com.hy.parser.http.server;

import com.hy.parser.http.HttpParseException;
import com.hy.parser.http.HttpRequest;
import com.hy.parser.http.HttpResponse;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 本地模拟的Http服务器。
 *
 * @author hy 2018/2/23
 */
public class LocalHttpServer {

    @Test
    public void startServer() throws IOException, HttpParseException {
        ServerSocket serverSocket = new ServerSocket(1220);
        Socket socket = serverSocket.accept();
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        byte[] bytes = new byte[1024 * 10];
        int len;
        len = is.read(bytes);
        if (len != -1) {
            String s = new String(bytes, 0, len);
            System.out.println(s);
            HttpRequest httpRequest = HttpRequest.parseString(s);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append("hello, world! \n");
        }
        String content = sb.toString();

        SimpleDateFormat sdf = new SimpleDateFormat("E, ww MMM yyyy HH:mm:ss z", Locale.US);
        String date = sdf.format(new Date());

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.head = "HTTP/1.1 200 OK";
        httpResponse.Date = date;
        httpResponse.Server = "MediaPlayerProxy.java";
        httpResponse.LastModified = date;
        httpResponse.ETag = "\"9c38e0-55c1b887821c0\"";
        httpResponse.AcceptRanges = "bytes";
        httpResponse.ContentLength = content.getBytes().length + "";
        httpResponse.Connection = "Keep-Alive";
        httpResponse.ContentType = "text/html; charset=UTF-8";

        System.out.println(httpResponse.toString());

        os.write(httpResponse.toString().getBytes());
        os.write(content.getBytes());

        is.close();
        os.close();
        socket.close();
    }

    public static final String html = "\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>欢迎</title>\n" +
            "    " +
            "    <!--jquery-->\n" +
            "    <script type=\"text/javascript\" src=\"scripts/jq/jquery-3.2.1.min.js\"></script>\n" +
            "    <!--获取ip后台地址-->\n" +
            "    <script src='http://pv.sohu.com/cityjson?ie=utf-8'></script>\n" +
            "</head>\n" +
            "\n" +
            "<script>\n" +
            "    function showIP() {\n" +
            "        var ip = returnCitySN.cip;\n" +
            "        var city = returnCitySN.cname;\n" +
            "        var text = '您来自 ' + ip + ', 城市: ' + city + '。';\n" +
            "        $('#where_u_from').text(text);\n" +
            "    }\n" +
            "\n" +
            "    function showAnimation() {\n" +
            "        $('body').append(\"<script src='scripts/ball.js'><\\/script>\");\n" +
            "    }\n" +
            "\n" +
            "</script>\n" +
            "\n" +
            "\n" +
            "<body>\n" +
            "<div style=\"height: 45px\">\n" +
            "    <h2 id='where_u_from'>\n" +
            "        <button type=\"button\" class=\"s_btn\" onclick=\"showIP()\">显示你的IP</button>\n" +
            "    </h2>\n" +
            "</div>\n" +
            "\n" +
            "<!--<button type=\"button\" id=\"ba\" onclick=\"showAnimation()\">加载动画，需要耗费4k流量</button>-->\n" +
            "\n" +
            "<h1><a href=\"app/bs.apk\">毕业设计app下载(1.8M)</a></h1>\n" +
            "<h1><a href=\"app/mqttchatlite.apk\">mqtt轻聊下载(840k)</a></h1>\n" +
            "<h2>\n" +
            "    Android端打开APP:<p></p>\n" +
            "    <a class='appLink' href='weixin://'>微信</a>\n" +
            "    <a class='appLink' href='mqq://'>QQ</a>\n" +
            "    <a class='appLink' href='taobao://'>淘宝</a>\n" +
            "    <a class='appLink' href='openjd://'>京东</a>\n" +
            "    <a class='appLink' href='zhihu://'>知乎</a>\n" +
            "    <a class='appLink' href='sinaweibo://browser/'>微博</a>\n" +
            "\n" +
            "    <a class='appLink' href='ofoapp://'>ofo</a>\n" +
            "    <a class='appLink' href='orpheus://'>网易云音乐</a>\n" +
            "    <a class='appLink' href='kugou://'>酷狗音乐</a>\n" +
            "\n" +
            "\n" +
            "</h2>\n" +
            "\n" +
            "<!--<script src=\"scripts/ball.js\"></script>-->\n" +
            "</body>\n";

}
