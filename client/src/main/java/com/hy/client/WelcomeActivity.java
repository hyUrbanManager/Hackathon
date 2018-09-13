package com.hy.client;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hy.androidlib.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

// 欢迎功能选择界面。
public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.mGridView)
    GridView mGridView;

    private SimpleAdapter mAdapter;

    // 启动的Activity项目。
    private List<Class<?>> activities = new ArrayList<>();

    // 标题。
    private List<String> titles = new ArrayList<>();

    // 数据。
    private ArrayList<HashMap<String, Object>> data = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        // 切换回原始theme。
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        activities.add(ContentPCActivity.class);
        titles.add("ContentProvider读取数据库");

        activities.add(LoadJPEGActivity.class);
        titles.add("相机大图片加载优化");

        activities.add(ViewStubActivity.class);
        titles.add("viewStub优化布局");

        activities.add(QRCodeActivity.class);
        titles.add("网址生成二维码");

        activities.add(QQShareActivity.class);
        titles.add("qq分享");

        activities.add(CanvasActivity.class);
        titles.add("测试画布");

        activities.add(TouchEventActivity.class);
        titles.add("测试点击事件分发");

        activities.add(LottieActivity.class);
        titles.add("测试lottie动画");

        activities.add(ViewActivity.class);
        titles.add("测试View布局滑动");

        ToastUtil.init(getApplicationContext());

        // 初始化数据。
        for (int i = 0; i < titles.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("text", titles.get(i));
            map.put("layout", randomBackgroundColor(i));
            data.add(map);
        }

        mAdapter = new SimpleAdapter(this,
                data,
                R.layout.item_simple_image_center_text,
                new String[]{"text", "layout"},
                new int[]{R.id.mText, R.id.mFrameLayout});
        mAdapter.setViewBinder((view, data, textRepresentation) -> {
            if (view instanceof TextView) {
                ((TextView) view).setText((CharSequence) data);
            } else if (view instanceof FrameLayout) {
                view.setBackgroundColor((Integer) data);
            } else {
                return false;
            }
            return true;
        });

        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener((parent, view, position, id) -> {
            startActivity(new Intent(WelcomeActivity.this, activities.get(position)));
        });
    }

    private Random random = new Random();

    // 随机颜色背景。
    private int randomBackgroundColor(int index) {
        float h = random.nextFloat() * 360;
        float s = random.nextFloat();
        float v = 0.5f;
        return Color.HSVToColor(0x80, new float[]{h, s, v});
    }

}
