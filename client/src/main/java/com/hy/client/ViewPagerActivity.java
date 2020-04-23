package com.hy.client;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 测试ViewPager更换数据 adpter
 *
 * @author huangye
 */
public class ViewPagerActivity extends AppCompatActivity {

    private static final String TAG = "@ViewPagerActivity";

//    @BindView(R.id.pager_tab_strip)
//    PagerTabStrip mPagerTabStrip;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.button)
    Button mButton;

    private List<Item> mItemList1 = new ArrayList<>();
    private List<Item> mItemList2 = new ArrayList<>();

    private Adapter mAdapter = new Adapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ButterKnife.bind(this);

        initData();

        mAdapter.mCurrentItems = mItemList1;

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(1);

        mButton.setOnClickListener(v -> {
            if (mAdapter.mCurrentItems == mItemList1) {
                mAdapter.mCurrentItems = mItemList2;
                mAdapter.notifyDataSetChanged();
                mButton.setText("切换到ItemList1");
            } else {
                mAdapter.mCurrentItems = mItemList1;
                mAdapter.notifyDataSetChanged();
                mButton.setText("切换到ItemList2");
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            Item item = new Item();
            item.text = "item " + i;
            Item item2 = new Item();
            item2.text = "选项 " + i;
            mItemList1.add(item);
            mItemList2.add(item2);
        }
    }

    class Adapter extends PagerAdapter {

        List<Item> mCurrentItems = new ArrayList<>();

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(ViewPagerActivity.this, R.layout.item_view_pager, null);
            TextView textView = view.findViewById(R.id.text);
            textView.setText(mCurrentItems.get(position).text);
            container.addView(view);
            Log.d(TAG, "instantiateItem: " + mCurrentItems.get(position).text);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.d(TAG, "destroyItem: " + mCurrentItems.get(position).text + ", obj: " + object);
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mCurrentItems.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            View view = (View) object;
            if (TAG.equals(view.getTag())) {
                return POSITION_UNCHANGED;
            } else {
                Log.i(TAG, "POSITION_NONE, " + object);
                return POSITION_NONE;
            }
        }
    }

    class Item {
        String text;
    }
}
