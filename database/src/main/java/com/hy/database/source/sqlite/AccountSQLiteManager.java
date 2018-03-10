package com.hy.database.source.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hy.androidlib.Logcat;

/**
 * Created by Administrator on 2018/3/10.
 *
 * @author hy 2018/3/10
 */
public class AccountSQLiteManager extends SQLiteOpenHelper {
    private static final String TAG = "@AccountSQLiteManager";

    public static final String NAME = "account";
    public static final int version = 1;

    public AccountSQLiteManager(Context context) {
        super(context, NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Logcat.i(TAG, "database version: " + db.getVersion());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
