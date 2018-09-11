package com.sollian.bottomsheetdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * @author admin on 2018/9/10.
 */
public class BaseFragment extends Fragment {
    protected final String TAG = getClass().getName();

    public void show(FragmentActivity activity, int containerId) {
        activity.getSupportFragmentManager().beginTransaction()
                .add(containerId,this, TAG)
                .commitAllowingStateLoss();
    }
    public void close() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .remove(this)
                .commitAllowingStateLoss();
    }
}
