package com.wzx.app.smartui;


import android.support.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by WangZhanXian on 2018/3/23.
 * 给每个fragment配置对应的Activity
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface LayoutId {
	@LayoutRes int value();
}
