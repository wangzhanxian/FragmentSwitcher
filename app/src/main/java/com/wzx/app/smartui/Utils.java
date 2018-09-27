package com.wzx.app.smartui;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

public class Utils {

    public static @LayoutRes
    int getLayoutId(@NonNull Object object) {
        LayoutId annotation = object.getClass().getAnnotation(LayoutId.class);
        if (annotation == null) {
            throw new RuntimeException("the class (" + object.getClass() + ") missing the config with @LayoutId");
        }
        return annotation.value();
    }
}
