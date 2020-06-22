package com.hy.client;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Spinner控件配合scrollView会滑动到区域之外。
 *
 * @author huangye
 */
public class DialogShowActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button mButton;

    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_show);
        ButterKnife.bind(this);

        mButton.setOnClickListener(v -> {
            Dialog dialog = new Dialog(this);
            mView = View.inflate(this, R.layout.layout_dialog_spinner, null);

            dialog.addContentView(mView, new ViewGroup.LayoutParams(1200, 600));
            dialog.show();
        });
    }

}
