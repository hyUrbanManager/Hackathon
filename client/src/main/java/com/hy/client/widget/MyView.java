package com.hy.client.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;

/**
 * Created by huangye on 2018/7/30.
 *
 * @author hy 2018/7/30
 */
public class MyView extends AppCompatSeekBar {

    private Paint mPaint;
    private Paint mSidePaint;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mSidePaint = new Paint();
        mSidePaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 画边线。
        canvas.drawLine(0, 0, 0, getHeight() - 1, mSidePaint);
        canvas.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight() - 1, mSidePaint);
        canvas.drawLine(0, 0, getWidth() - 1, 0, mSidePaint);
        canvas.drawLine(0, getHeight() - 1, getWidth() - 1, getHeight() - 1, mSidePaint);

        canvas.rotate(-90);
        canvas.drawLine(0, -50, 100, -50, mPaint);
        super.onDraw(canvas);
    }
}
