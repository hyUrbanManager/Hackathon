package com.hy.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hy.client.widget.MyView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CanvasActivity extends AppCompatActivity {

    @BindView(R.id.my_view)
    MyView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canva);
        ButterKnife.bind(this);

        myView.setMax(100);
        myView.setProgress(50);
    }
}
