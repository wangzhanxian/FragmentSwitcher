package com.wzx.app.fastui.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.wzx.app.fastui.annotions.Host;

public class ComnUtil {

    public static Class<? extends FragmentActivity> getContainerActivity(@NonNull Object object) {
        Host annotation = object.getClass().getAnnotation(Host.class);
        return annotation != null? annotation.value():null;
    }
}
