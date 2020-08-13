package com.hy.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hy.client.widget.MarqueeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarqueeActivity extends AppCompatActivity {

    private static final String TAG = "@MarqueeAct";

    @BindView(R.id.marque_view)
    MarqueeView mMarqueeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marquee);
        ButterKnife.bind(this);

        mMarqueeView.startScroll("hello, world. 布尔沃曾经说过，要掌握书，莫被书掌握；" +
                "要为生而读，莫为读而生。我希望诸位也能好好地体会这句话。", Integer.MAX_VALUE);
    }
}
