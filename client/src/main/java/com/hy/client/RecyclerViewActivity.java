package com.hy.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 1. 测试按键下后，底部的item是否显示完全。是否需要按2次下才能到下一个item。
 *
 * @author huangye
 */
public class RecyclerViewActivity extends AppCompatActivity {

    private static final String TAG = "@RecyclerViewAct";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private List<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);

        for (int i = 0; i < 20; i++) {
            mData.add("text " + i);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new Adp());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    class Adp extends RecyclerView.Adapter<Adp.H> {

        @Override
        public H onCreateViewHolder(ViewGroup parent, int viewType) {
            return new H(View.inflate(RecyclerViewActivity.this,
                    R.layout.layout_item_recycler_view, null));
        }

        @Override
        public void onBindViewHolder(H holder, int position) {
            holder.text.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class H extends RecyclerView.ViewHolder {

            @BindView(R.id.text)
            TextView text;

            public H(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
