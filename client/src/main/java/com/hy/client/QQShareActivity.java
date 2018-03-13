package com.hy.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.hy.androidlib.utils.ToastUtil;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QQShareActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;

    private Tencent mTencent;

    public static final String url = "http://139.199.170.98";
    public static final String title = "【震惊！奇怪的app都来自这里】";
    public static final String summary = "无意发现小伙伴手机暗藏神奇的app，" +
            "他们却说app都是在这里下载的...";
    public static final String imageUrl = "http://139.199.170.98/ye.png";
    public static final String appName = "微信";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqshare);
        ButterKnife.bind(this);

        mTencent = Tencent.createInstance("222222", this);

        button.setOnClickListener(v -> {
            Bundle params = new Bundle();
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
            mTencent.shareToQQ(this, params, new BaseUiListener());
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTencent != null) {
            mTencent.releaseResource();
        }
    }

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            ToastUtil.showToastShort("完成 " + o);
        }

        @Override
        public void onError(UiError uiError) {
            ToastUtil.showToastShort("错误" + uiError.errorMessage);
        }

        @Override
        public void onCancel() {
            ToastUtil.showToastShort("取消");
        }
    }
}
