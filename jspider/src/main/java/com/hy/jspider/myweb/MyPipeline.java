package com.hy.jspider.myweb;

import com.hy.jspider.DbPipeline;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

/**
 * 保存进数据库。
 *
 * @author hy 2018/5/13
 */
public class MyPipeline implements DbPipeline {

    @Test
    public void testSql() {
        try {
            Connection connection = DriverManager.getConnection(mySqlServerUrl,
                    "hy",
                    "12345678");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from student");

            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");

                // 输出数据
                System.out.print("ID: " + id);
                System.out.print(", name: " + name);
                System.out.print(", age: " + age);
                System.out.print("\n");
            }

            statement.execute(
                    "insert into student(name, age) values ('小铛铛', 20)"
            );

            // 完成后关闭
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
            "jdbc:mysql://localhost:3306/sqlinjection" +
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
        List<String> list = resultItems.get("links");


    }

}
