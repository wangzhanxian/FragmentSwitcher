package com.wzx.app.fastui.annotions;

import com.wzx.app.fastui.LaunchMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Describe ：给fragment配置启动模式，如果不配置，默认为栈内复用
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mode {
    @LaunchMode int value() default LaunchMode.SINGLETASK;
}
