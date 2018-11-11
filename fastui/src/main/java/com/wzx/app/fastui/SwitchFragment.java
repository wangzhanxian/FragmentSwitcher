package com.wzx.app.fastui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import com.wzx.app.fastui.utils.FragmentUtil;

/**
 * Describe ：自定义Fragment
 */
public class SwitchFragment extends Fragment {

    protected String hostName() {
        Class<? extends FragmentActivity> hostActivity = FragmentUtil.getHostActivity(this);
        return hostActivity != null ? hostActivity.getName() : null;
    }

    /**
     * 类似onNewIntent方法
     *
     * @param bundle
     */
    public void onNewBundle(Bundle bundle) {

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (onBackPressed()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否处理返回键
     *
     * @return
     */
    public boolean onBackPressed() {
        return false;
    }

}
