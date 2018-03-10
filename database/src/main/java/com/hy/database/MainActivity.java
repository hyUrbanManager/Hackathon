package com.hy.database;

import android.content.ContentResolver;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hy.androidlib.utils.ToastUtil;
import com.hy.database.source.sqlite.AccountSQLiteManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "@MainActivity";

    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.editText1)
    EditText editText1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.editText2)
    EditText editText2;
    @BindView(R.id.button1)
    Button button1;

    private ContentResolver resolver;
    private AccountSQLiteManager sqLiteManager;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ToastUtil.init(getApplicationContext());

        sqLiteManager = new AccountSQLiteManager(this);
        database = sqLiteManager.getWritableDatabase();
        resolver = getContentResolver();

        button1.setOnClickListener(v -> {
            String account = editText1.getText().toString();
            String userName = editText2.getText().toString();
            if (account.length() > 20 || userName.length() > 20) {
                ToastUtil.showToastLong("content len is not allowed!");
                return;
            }

            database.execSQL("insert into account (account, username) values (" +
                    "'" + account + "'," +
                    "'" + userName + "'" +
                    ");");
            ToastUtil.showToastShort("插入数据成功");
        });
    }
}
