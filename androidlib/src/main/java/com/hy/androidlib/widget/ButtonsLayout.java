package com.hy.androidlib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.hy.androidlib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 装载buttons的自定义Layout。
 *
 * @author hy 2018/2/27
 */
public class ButtonsLayout extends GridView {

    // 按键信息。
    private List<BtnInfo> buttons;
    private Adp adapter;

    public ButtonsLayout(Context context) {
        this(context, null);
    }

    public ButtonsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ButtonsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        buttons = new ArrayList<>();
        adapter = new Adp();
        setAdapter(adapter);
        setNumColumns(3);
    }

    public void addButtons(String text, OnClickListener listener) {
        buttons.add(new BtnInfo(text, listener));
        adapter.notifyDataSetChanged();
    }

    private class Adp extends BaseAdapter {

        @Override
        public int getCount() {
            return buttons.size();
        }

        @Override
        public Object getItem(int position) {
            return buttons.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.special_button, parent, false);
            }
            Button button = (Button) convertView;
            button.setText(buttons.get(position).text);
            button.setOnClickListener(buttons.get(position).listener);
            return button;
        }
    }

    private class BtnInfo {
        final String text;
        final OnClickListener listener;

        public BtnInfo(String text, OnClickListener listener) {
            this.text = text;
            this.listener = listener;
        }
    }
}
