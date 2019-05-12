package com.hy.client;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class LayerDrawableActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layer_drawable);
        mImageView = findViewById(R.id.image);

        // it work.
//        mImageView.setImageResource(R.drawable.ig_with_shadow);

        // .9.png先画，画完后自动设置padding。
        Drawable srcDrawable = getResources().getDrawable(R.drawable.yasuo);
        Drawable bg9NinePatchDrawable = getResources().getDrawable(R.drawable.shadow);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{bg9NinePatchDrawable, srcDrawable});

        mImageView.setImageDrawable(layerDrawable);
    }
}
