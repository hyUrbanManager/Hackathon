package com.hy.jspider.ess;

import com.hy.jspider.Downloader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 邪恶漫画图片下载。
 *
 * @author hy 2018/5/14
 */
public class XemhDownloader extends Downloader {

    // load Driver.
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // url.
    public static final String mySqlServerUrl =
            "jdbc:mysql://localhost:3306/ess" +
                    "?useSSL=false" +
                    "&serverTimezone=CST" +
                    "&useUnicode=true&characterEncoding=utf8";
    public static final String user = "hy";
    public static final String password = "12345678";

    private Connection connection;
    private Statement statement;

    @Override
    public void startDownload() {
        List<String> urls = new ArrayList<>();
        List<String> names = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(mySqlServerUrl,
                    user, password);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select title, url from ess_xemh");
            while (rs.next()) {
                String title = rs.getString("title").replace("邪恶漫画全集", "");
                if (title.length() != 0) {
                    urls.add(rs.getString("url"));
                    names.add(title);
                }
            }
            download(urls, names);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDownLoadDir() {
        return "/mnt/sd_card/xemh";
    }
}
