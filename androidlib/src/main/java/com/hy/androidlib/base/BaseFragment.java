package com.hy.androidlib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.hy.androidlib.utils.LeakCanaryManager;

/**
 * Fragment的基类
 *
 * @author zqjasonZhong
 *         date : 2017/11/10
 */
public class BaseFragment extends Fragment {
    protected String TAG = getClass().getSimpleName();
    private Toast mToast;
    private Bundle bundle;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LeakCanaryManager.getInstance().watch(this);
    }

    /**
     * 切换fragment
     *
     * @param containerId 控件id
     * @param fragment    切换fragment
     * @param fragmentTag fragment tag
     */
    public void changeFragment(int containerId, Fragment fragment, String fragmentTag) {
        if (fragment != null && getActivity() != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            if (fragmentManager != null) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (fragmentTransaction != null) {
                    if (!TextUtils.isEmpty(fragmentTag)) {
                        fragmentTransaction.replace(containerId, fragment, fragmentTag);
                    } else {
                        fragmentTransaction.replace(containerId, fragment);
                    }
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commitAllowingStateLoss();
                }
            }
        }
    }

    public void changeFragment(int containerId, Fragment fragment) {
        changeFragment(containerId, fragment, null);
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }


    public void showToastShort(String info) {
        showToast(info, Toast.LENGTH_SHORT);
    }

    public void showToastShort(int info) {
        showToastShort(getResources().getString(info));
    }

    public void showToastLong(String msg) {
        showToast(msg, Toast.LENGTH_LONG);
    }

    public void showToastLong(int msg) {
        showToastLong(getResources().getString(msg));
    }

    /**
     * 显示内容
     *
     * @param msg      内容
     * @param duration 显示间隔
     */
    public void showToast(String msg, int duration) {
        if (!TextUtils.isEmpty(msg) && duration >= 0) {
            if (mToast == null) {
                mToast = Toast.makeText(getContext().getApplicationContext(), msg, duration);
            } else {
                mToast.setText(msg);
                mToast.setDuration(duration);
            }
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
        }
    }
}
