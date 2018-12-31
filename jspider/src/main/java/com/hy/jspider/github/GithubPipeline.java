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
            "jdbc:mysql://localhost:3306/github" +
                    "?useSSL=false" +
                    "&serverTimezone=CST" +
                    "&useUnicode=true&characterEncoding=utf8";
    public static final String user = "hy";
    public static final String password = "12345678";

    private Connection connection;
    private Statement statement;

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
    }

    /**
     * 创建表。
     */
    private void createTable() {
        try {
            statement.execute("create table if not exists " +
                    "github_search(id int not null primary key auto_increment, " +
                    "keyword varchar(32), " +
                    "title varchar(100), " +
                    "language varchar(32), " +
                    "stars varchar(16), " +
                    "description varchar(512)" +
                    ") " +
                    "charset utf8 collate utf8_general_ci");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

        String keyword = resultItems.get("keyword");
        List<String> titles = resultItems.get("titles");
        List<String> languages = resultItems.get("languages");
        List<String> starses = resultItems.get("starses");
        List<String> descriptions = resultItems.get("descriptions");

        if (titles == null) {
            return;
        }

        int index = -1;
        while (++index < titles.size()) {
            String title = titles.get(index).replaceAll("'","\\\\'");
            String language = languages.get(index).replaceAll("'","\\\\'");
            String stars = starses.get(index).replaceAll("'","\\\\'");
            String description = descriptions.get(index).replaceAll("'","\\\\'");

            try {
                ResultSet set = statement.executeQuery("select title from github_search " +
                        "where title='" + title + "'");
                if (set.next()) {
                    statement.execute("update github_search set " +
                            "keyword='" +
                            keyword + "'," +
                            "title='" +
                            title + "'," +
                            "language='" +
                            language + "'," +
                            "stars='" +
                            stars + "'," +
                            "description='" +
                            description + "' where title='" + title +
                            "'");
                } else {
                    statement.execute("insert into github_search(keyword,title,language,stars,description) values ('" +
                            keyword + "','" +
                            title + "','" +
                            language + "','" +
                            stars + "','" +
                            description +
                            "')");
                }
                set.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
