package com.hy.database.source.sqlite;

/**
 * 数据库存储的Account对象以及相关信息。
 *
 * @author hy 2018/3/10
 */
public class Account {

    public static final String TABLE_NAME = "account";

    public int id;

    public String account;

    public String userName;

    public Account() {
    }

    public Account(int id, String account, String userName) {
        this.id = id;
        this.account = account;
        this.userName = userName;
    }
}
