package com.wzx.app.fastui.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Pair;

import com.wzx.app.fastui.LaunchMode;
import com.wzx.app.fastui.annotions.Host;
import com.wzx.app.fastui.annotions.Mode;

public class ComnUtil {

    public static final String FRAGMENT_CLASS_NAME = "fragment_class_name";
    public static final String FRAGMENT_EXTRA_BUNDLE = "fragment_extra_bundle";

    public static Class<? extends FragmentActivity> getContainerActivity(@NonNull Object object) {
        Class clazz = object instanceof Class ? (Class) object : object.getClass();
        Host annotation = (Host) clazz.getAnnotation(Host.class);
        return annotation != null ? annotation.value() : null;
    }

    public static @LaunchMode int getLaunchMode(@NonNull Object object) {
        Class clazz = object instanceof Class ? (Class) object : object.getClass();
        Mode annotation = (Mode) clazz.getAnnotation(Mode.class);
        return annotation != null ? annotation.value() : LaunchMode.SINGLETASK;
    }

    public static Class loadClass(ClassLoader loader, String className) {
        try {
            if (className == null){
                return null;
            }
            return loader.loadClass(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Intent setTargetToIntent(Intent intent, String fragmentName, Bundle fragmentArgs) {
        if (fragmentName != null) {
            intent.putExtra(FRAGMENT_CLASS_NAME, fragmentName);
        }
        if (fragmentArgs != null) {
            intent.putExtra(FRAGMENT_EXTRA_BUNDLE, fragmentArgs);
        }
        return intent;
    }

    public static Pair<String, Bundle> getTargetFromIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                return new Pair<>(bundle.getString(FRAGMENT_CLASS_NAME), bundle.getBundle(FRAGMENT_EXTRA_BUNDLE));
            }
        }
        return new Pair<>(null,null);
    }
}
