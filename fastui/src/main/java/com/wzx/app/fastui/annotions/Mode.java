package com.wzx.app.fastui.annotions;

import com.wzx.app.fastui.LaunchMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 给fragment配置默认的Activity，如果不配置，默认在当前Activity切换
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mode {
    @LaunchMode int value() default LaunchMode.SINGLETASK;
}
