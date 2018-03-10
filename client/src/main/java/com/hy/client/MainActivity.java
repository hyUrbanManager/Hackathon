package com.hy.client;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hy.androidlib.Logcat;
import com.hy.client.bean.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "@MainActivity";

    private static final Uri URI_GET_ACCOUT = Uri.parse("content://com.hy.provider.AccountProvider/getAccount");

    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Adp adapter = new Adp();
    private List<Account> list = new ArrayList<>();

    private ContentResolver resolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        resolver = getContentResolver();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        String type = resolver.getType(URI_GET_ACCOUT);
        type = type == null ? "数据库app未启动" : type;
        text.setText(type);
        updateData();
    }

    private void updateData() {
        list.clear();
        adapter.notifyDataSetChanged();
        Cursor cursor = resolver.query(URI_GET_ACCOUT, null, null, null, null);
        Logcat.i(TAG, "cursor: " + cursor);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String account = cursor.getString(cursor.getColumnIndex("account"));
                String userName = cursor.getString(cursor.getColumnIndex("username"));
                Account account1 = new Account(id, account, userName);
                list.add(account1);
            }
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }

    class Adp extends RecyclerView.Adapter<Adp.H> {

        @Override
        public H onCreateViewHolder(ViewGroup parent, int viewType) {
            return new H(LayoutInflater.from(MainActivity.this).inflate(R.layout.item_accout, parent, false));
        }

        @Override
        public void onBindViewHolder(H holder, int position) {
            Account account = list.get(position);
            holder.info.setText(String.format(Locale.CHINA, "%d %s %s",
                    account.id, account.account, account.userName));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class H extends RecyclerView.ViewHolder {

            @BindView(R.id.info)
            TextView info;

            public H(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
