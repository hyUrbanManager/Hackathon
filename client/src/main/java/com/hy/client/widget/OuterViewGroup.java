package com.hy.client.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.hy.client.ViewActivity;

/**
 * 外部ViewGroup。
 *
 * @author hy 2018/6/14
 */
public class OuterViewGroup extends ViewGroup {

    public static final String TAG = "@OuterViewGroup";

    public OuterViewGroup(Context context) {
        super(context);
    }

    public OuterViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OuterViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        Log.d(TAG, "measure mode: " + ViewActivity.measureModeToString(mode));
        Log.v(TAG, "id: " + getId());

        Log.v(TAG, "onMeasure width: " + MeasureSpec.getSize(widthMeasureSpec) +
                ", height: " + MeasureSpec.getSize(heightMeasureSpec));

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(l, t, r, b);
        }

    }
}
