package com.hy.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppLinkActivity extends AppCompatActivity {

    @BindView(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_link);
        ButterKnife.bind(this);

        setTitle("appLink");

        Intent intent = getIntent();
        StringBuilder sb = new StringBuilder();
        sb.append("intent:\n");
        sb.append("action: ").append(intent.getAction()).append("\n\n");
        sb.append("categories: \n");
        Set<String> set = intent.getCategories();
        for (String s : set) {
            sb.append(s).append('\n');
        }
        sb.append('\n');

        sb.append("data: ").append(intent.getDataString()).append("\n\n");

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            sb.append("bundle: \n");
            Set<String> keySet = bundle.keySet();
            for (String key : keySet) {
                sb.append("key: ").append(key).append('\n');
                sb.append("value: ").append(bundle.getString(key)).append('\n');
            }
        }

        text.setText(sb.toString());
    }
}
