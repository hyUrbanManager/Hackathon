package com.hy.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hy.androidlib.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewStubActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.viewStub)
    ViewStub viewStub;

    LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stub);
        ButterKnife.bind(this);

        button.setOnClickListener(v -> {
            if (viewStub != null) {
                linear = (LinearLayout) viewStub.inflate();
            } else {
                ToastUtil.showToastLong("已经加载了布局");
            }
        });

        viewStub.setOnInflateListener((stub, inflated) -> {
            ToastUtil.showToastShort("加载布局完毕");
            viewStub = null;
        });
    }
}
