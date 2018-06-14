package com.hy.client.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hy.client.ViewActivity;

/**
 * 内部View。
 *
 * @author hy 2018/6/14
 */
public class InnerView extends View {
    private static final String TAG = "@InnerView";

    public InnerView(Context context) {
        super(context);
    }

    public InnerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InnerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        Log.d(TAG, "measure mode: " + ViewActivity.measureModeToString(mode));

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                width = 50;
                height = 50;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.v(TAG, "id: " + getId());
        Log.v(TAG, "width: " + getWidth() + ", height: " + getHeight());

        @SuppressLint("ResourceType")
        int which = getId() % 3;
        int[] cs = {Color.BLUE, Color.RED, Color.GREEN};
        canvas.drawColor(cs[which]);


    }
}
