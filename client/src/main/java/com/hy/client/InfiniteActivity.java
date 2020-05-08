package com.hy.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfiniteActivity extends AppCompatActivity {

    private static final String KEY_NUM_EXTRA = "num";

    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.button)
    Button mButton;

    private int mNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infinite);
        ButterKnife.bind(this);

        mNum = getIntent().getIntExtra(KEY_NUM_EXTRA, 0);

        setTitle("无限打开Activity " + mNum);
        mText.setText("InfiniteActivity " + mNum);
    }

    @OnClick(R.id.button)
    public void onClick() {
        Intent intent = new Intent(this, InfiniteActivity.class);
        intent.putExtra(KEY_NUM_EXTRA, mNum + 1);
        startActivity(intent);
    }
}
