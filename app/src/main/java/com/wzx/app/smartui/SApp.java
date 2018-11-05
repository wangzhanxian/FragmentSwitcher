package com.wzx.app.smartui;

import android.app.Application;

import com.wzx.app.fastui.SwitchHelper;

public class SApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //设置全局动画
        SwitchHelper.setGlobalCustomAnimations(R.anim.anim_enter_right_to_left,R.anim.anim_exit_right_to_left,
                R.anim.anim_enter_left_to_right,R.anim.anim_exit_left_to_right);
    }
}
