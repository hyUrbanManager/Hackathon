package com.hy.client.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hy.client.R;


/**
 * SurfaceView使用绘制。在8386可能有问题，8386系统本身问题。
 * <p>
 * 1、线程绘制时间尽可能快
 * 2、字符串占用内存优化、绘制次数优化。传入超长字符、超多次数也能正常运行。
 */
public class MarqueeView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "@MarqueeView";

    private static final int SCROLL_FROM_RIGHT_TO_LEFT = 0;
    private static final int SCROLL_FROM_LEFT_TO_RIGHT = 1;
    private static final int SCROLL_START = 0;
    private static final int SCROLL_END = 1;

    private static final int INTERVAL_STRING_LEN = 40;

    private OnMarqueeListener mOnMarqueeListener;

    private float mTextSize;
    private int mTextColor;
    private int mRepeatCounts;
    // 开始滚动的位置  0是从最左面开始    1是从最末尾开始
    private int mStartPoint;
    // 滚动方向 0 向左滚动   1 向右滚动
    private int mDirection;
    private float mSpeedXStep;
    private int mSpeed;

    private SurfaceHolder mSurfaceHolder;
    private TextPaint mTextPaint;
    private MarqueeViewThread mThread;
    private String mMarqueeString;
    private float mLayoutHeight;

    private float mTextWidth;
    private float mTextBlankWidth;
    private float mCurrentX;
    private float mTextAreaEndX;

    public MarqueeView(Context context) {
        this(context, null);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.mSurfaceHolder = holder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.mSurfaceHolder = holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mThread != null) {
            mThread.mIsRun = false;
        }
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MarqueeView, defStyleAttr, 0);
        mTextColor = a.getColor(R.styleable.MarqueeView_textColor, Color.WHITE);
        mTextSize = a.getDimension(R.styleable.MarqueeView_textSize, 48);
        mRepeatCounts = a.getInt(R.styleable.MarqueeView_repeatCount, 1);
        mStartPoint = a.getInt(R.styleable.MarqueeView_startPoint, 0);
        mDirection = a.getInt(R.styleable.MarqueeView_direction, 0);
        // need implement
        mSpeed = a.getInt(R.styleable.MarqueeView_speed, 20);
        a.recycle();

        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTypeface(Typeface.SANS_SERIF);
        mTextPaint.setFakeBoldText(true);
        mLayoutHeight = getContext().getResources().getDimension(R.dimen.broadcast_layout_height);
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        mTextAreaEndX = getContext().getResources().getDimension(R.dimen.broadcast_content_end);
        mSpeedXStep = mTextAreaEndX * 0.001f;
        Log.d(TAG, "init: step: " + mSpeedXStep);
    }

    protected void measurementsText(String msg) {
        mMarqueeString = msg;
        mTextWidth = mTextPaint.measureText(mMarqueeString);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < INTERVAL_STRING_LEN; i++) {
            sb.append(' ');
        }
        mTextBlankWidth = mTextPaint.measureText(sb.toString());
        Log.d(TAG, "mTextWidth: " + mTextWidth + ". mTextBlankWidth: " + mTextBlankWidth);
        resetScrollStartPosition();
    }

    private void resetScrollStartPosition() {
        if (mStartPoint == SCROLL_START) {
            mCurrentX = 0;
        } else if (mStartPoint == SCROLL_END) {
            mCurrentX = mTextAreaEndX;
        } else {
            mCurrentX = mTextAreaEndX;
        }
        Log.d(TAG, "resetScrollStartPosition: " + mCurrentX);
    }

    public void startScroll(String msg, int loopTime) {
        mRepeatCounts = loopTime;
        if (!TextUtils.isEmpty(msg)) {
            measurementsText(msg);
        }

        if (mThread != null && mThread.mIsRun) {
            mThread.mIsRun = false;
            mThread.interrupt();
        }
        mThread = new MarqueeViewThread(mSurfaceHolder);
        mThread.mIsRun = true;
        mThread.start();
    }

    public boolean isScrolling() {
        return mThread != null && mThread.mIsRun;
    }

    public void stopScroll() {
        if (mThread != null) {
            mThread.mIsRun = false;
            mThread.interrupt();
        }
        mThread = null;
    }

    private class MarqueeViewThread extends Thread {
        private final SurfaceHolder mHolder;
        private boolean mIsRun;
        private float mBaselineY;
        private int mLastIndex;

        MarqueeViewThread(SurfaceHolder holder) {
            this.mHolder = holder;
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            float textHeight = fontMetrics.bottom - fontMetrics.top;
            mBaselineY = (mLayoutHeight - textHeight) / 2 + textHeight - fontMetrics.descent;
            Log.d(TAG, "mBaselineY: " + mBaselineY + ". fontMetrics: " + fontMetrics.descent);
        }

        private void onDraw() {
            Canvas canvas = null;
            synchronized (mHolder) {
                try {
                    canvas = mHolder.lockCanvas();
                    if (canvas != null) {
                        calculateCurrentPosition();
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        boolean hasDraw = false;
                        final int startIndex = mLastIndex;
                        for (int i = startIndex; i < mRepeatCounts; i++) {
                            float startX = mCurrentX + i * (mTextWidth + mTextBlankWidth);
                            float endX = startX + mTextWidth;
                            // 是否有显示在屏幕内的区域
                            if (startX <= mTextAreaEndX && endX >= 0) {
                                hasDraw = true;
                                canvas.drawText(mMarqueeString, startX, mBaselineY, mTextPaint);
                            }

                            // 该序号的字符串以前的不需要显示
                            if (endX < 0) {
                                mLastIndex = i + 1;
                                Log.i(TAG, "mLastIndex update: " + mLastIndex);
                            }
                            // 该序号的字符串往后不需要显示
                            if (startX > mTextAreaEndX) {
                                break;
                            }
                        }
                        if (!hasDraw) {
                            Log.d(TAG, "nothing draw, end.");
                            if (mIsRun) {
                                chargeScrollComplete();
                            } else {
                                Log.w(TAG, getName() + " thread is canceled. not notify.");
                            }
                        }
                    }
                    Log.v(TAG, "onDraw");
                } catch (Exception e) {
                    Log.e(TAG, "onDraw error. " + e.getMessage());
                } finally {
                    if (canvas != null) {
                        try {
                            mHolder.unlockCanvasAndPost(canvas);
                        } catch (Exception e) {
                            Log.e(TAG, "unlockCanvasAndPost error. " + e.getMessage());
                        }
                    }
                }
            }
        }

        private void calculateCurrentPosition() {
            if (mDirection == SCROLL_FROM_RIGHT_TO_LEFT) {
                mCurrentX -= mSpeedXStep;
            } else if (mDirection == SCROLL_FROM_LEFT_TO_RIGHT) {
                mCurrentX += mSpeedXStep;
            }
        }

        @Override
        public void run() {
            while (mIsRun) {
                onDraw();
            }
        }
    }

    private void chargeScrollComplete() {
        post(() -> {
            stopScroll();
            if (mOnMarqueeListener != null) {
                mOnMarqueeListener.onRollOver();
            }
        });
    }

    public interface OnMarqueeListener {
        void onRollOver();
    }

    public void setOnMarqueeListener(OnMarqueeListener onMarqueeListener) {
        if (mOnMarqueeListener == null) {
            this.mOnMarqueeListener = onMarqueeListener;
        }
    }

    public void removeOnMarqueeListener() {
        this.mOnMarqueeListener = null;
    }
}
