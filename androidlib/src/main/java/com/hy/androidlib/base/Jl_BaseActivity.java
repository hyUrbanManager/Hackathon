package com.hy.androidlib.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.jieli.component.utils.LeakCanaryManager;


/**
 * @author zqjasonZhong
 *         date : 2017/11/10
 */
public class Jl_BaseActivity extends AppCompatActivity {
    protected String TAG = getClass().getSimpleName();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LeakCanaryManager.getInstance().watch(this);
    }

    /**
     * 切换fragment
     *
     * @param containerId layout id
     * @param fragment    fragment
     * @param fragmentTag fragment tag
     */

    public void changeFragment(int containerId, Fragment fragment, String fragmentTag) {
        if (fragment != null && !isFinishing()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
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

    /**
     * 切换fragment
     *
     * @param containerId layout id
     * @param origin      fragment
     * @param target      fragment
     * @param fragmentTag fragment tag
     */

    public void changeFragment(int containerId, Fragment origin, Fragment target, String fragmentTag) {
        if (target != null && !isFinishing()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager != null) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (fragmentTransaction != null) {
                    if (!target.isAdded()) {
                        if (!TextUtils.isEmpty(fragmentTag)) {
                            fragmentTransaction.add(containerId, target, fragmentTag);
                        } else {
                            fragmentTransaction.add(containerId, target);
                        }
                    }
                    if (origin != null) {
                        fragmentTransaction.hide(origin);
                    }
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.show(target);
                    fragmentTransaction.commitAllowingStateLoss();
                }
            }
        }
    }

    /**
     * 切换fragment(不带tag)
     *
     * @param containerId layout id
     * @param fragment    target fragment
     */
    public void changeFragment(int containerId, Fragment fragment) {
        changeFragment(containerId, fragment, null);
    }

}
