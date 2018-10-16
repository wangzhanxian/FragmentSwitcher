package com.wzx.app.fastui;

import android.support.annotation.IntDef;

import static com.wzx.app.fastui.LaunchMode.SINGLETASK;
import static com.wzx.app.fastui.LaunchMode.STANDRAD;

@IntDef({SINGLETASK,STANDRAD})
public @interface LaunchMode {

    /**
     * 栈内复用
     */
    int SINGLETASK = 0;

    /**
     * 多实例模式
     */
    int STANDRAD = 1;
}
