package com.hy.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hy.androidlib.Logcat;
import com.hy.database.source.sqlite.Account;
import com.hy.database.source.sqlite.AccountSQLiteManager;

/**
 * 提供账号查询。
 *
 * @author hy 2018/3/10
 */
public class AccountProvider extends ContentProvider {
    private static final String TAG = "@AccountProvider";

    public static final String authority = "com.hy.provider.AccountProvider";
    /**
     * uri类型。
     * content://com.hy.provider.AccountProvider/getAccount
     */
    public static final int ACCOUNT_GET = 1;
    /**
     * uri类型。
     * content://com.hy.provider.AccountProvider/login?username="123"&&password="456"
     */
    public static final int ACCOUNT_LOGIN = 2;

    private static UriMatcher uriMatcher;

    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase database;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(authority, "/getAccount", ACCOUNT_GET);
        uriMatcher.addURI(authority, "/login", ACCOUNT_GET);
    }

    @Override
    public boolean onCreate() {
        Logcat.i(TAG, "onCreate");
        sqLiteOpenHelper = new AccountSQLiteManager(getContext());
        database = sqLiteOpenHelper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int code = uriMatcher.match(uri);
        switch (code) {
            case ACCOUNT_GET:
                return database.rawQuery("select * from " + Account.TABLE_NAME, selectionArgs);
            case ACCOUNT_LOGIN:
                return null;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int code = uriMatcher.match(uri);
        switch (code) {
            case ACCOUNT_GET:
                return "ACCOUNT_GET";
            case ACCOUNT_LOGIN:
                return "LOGIN";
            default:
                return "UNKNOWN_TYPE_URI";
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }
}
