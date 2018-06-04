package com.hy.jspider.github;

import com.hy.jspider.DbPipeline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

/**
 * 保存进数据库。
 *
 * @author hy 2018/5/13
 */
public class GithubPipeline implements DbPipeline {

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

    private List<String> savedUrls;

    /**
     * 连接打开数据库。
     */
    public void startDb() {
        try {
            connection = DriverManager.getConnection(mySqlServerUrl,
                    user, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createTable();
        savedUrls = readUrlList();
    }

    /**
     * 创建表。
     */
    private void createTable() {
        try {
            statement.execute("create table if not exists " +
                    "ess_xemh(id int not null primary key auto_increment, " +
                    "title varchar(50), " +
                    "url varchar(512)" +
                    ") " +
                    "charset utf8 collate utf8_general_ci");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取数据库已储存的url。
     *
     * @return
     */
    private List<String> readUrlList() {
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery("select url from ess_xemh");
            while (rs.next()) {
                list.add(rs.getString("url"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 关闭数据库。
     */
    public void endDb() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
//        String title = resultItems.get("title");
//        String url = resultItems.get("url");
//        if (savedUrls.contains(url)) {
//            System.out.println("mysql has been saved this url result.");
//            return;
//        }
//        try {
//            statement.execute("insert into ess_xemh(title, url) values ('" +
//                    title + "','" +
//                    url +
//                    "')");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
