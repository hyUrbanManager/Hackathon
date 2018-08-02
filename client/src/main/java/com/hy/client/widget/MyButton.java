package com.hy.client.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by huangye on 2018/8/1.
 *
 * @author hy 2018/8/1
 */
public class MyButton extends AppCompatButton {

    private static final String TAG = "@MyButton";

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, getTag() + " " + event.toString());
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (a++ > 10) {
                Log.d(TAG, getTag() + " false stop");
                a = 0;
                return false;
            }
        }
        return super.onTouchEvent(event);

//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                return super.onTouchEvent(event);
//            default:
//        }
//        return true;
    }

    private int a = 0;
}
