package com.wzx.app.fastui;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.widget.FrameLayout;


public class FragmentSwitcher extends FrameLayout {


    private UIContainer container;

    /**
     * 是否可以切换
     */
    private boolean canSwitch;

    public FragmentSwitcher(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FragmentSwitcher(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        String defalutFragmentName;
        TypedArray typedArray = null;
        try {
            typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FragmentSwitcher);
            //获取默认fragment类名
            defalutFragmentName = typedArray.getString(R.styleable.FragmentSwitcher_defaultFragmentName);
            //是否可以切换，默认可以切换
            canSwitch = typedArray.getBoolean(R.styleable.FragmentSwitcher_switch_enable,true);
        } finally {
            if (typedArray != null) {
                typedArray.recycle();
            }
        }
        if (!(getContext() instanceof FragmentActivity)) {
            throw new RuntimeException("the context must instanceof android.support.v4.app.FragmentActivity");
        }
        if (getId() == NO_ID) {
            setId(generateViewId());
        }
        container = new UIContainer((FragmentActivity) getContext()).setContainerId(getId()).setDefaultFragmentName(defalutFragmentName);
    }

    public FragmentSwitcher setDefalutFragmentName(String defalutFragmentName) {
        if (defalutFragmentName != null) {
            container.setDefaultFragmentName(defalutFragmentName);
        }
        return this;
    }

    public FragmentSwitcher setSwitchEnable(boolean switchEnable){
        canSwitch = switchEnable;
        return this;
    }

    public UIContainer getContainer() {
        return container;
    }

    public boolean checkCanSwitch() {
        return canSwitch;
    }

    public Fragment getCurFragment() {
        return container.getCurFragment();
    }


    public boolean canGoback() {
        return container.getStackSize() > 1;
    }

    /**
     * 反回上一个fragment
     */
    public void goback() {
        goback(null);
    }

    /**
     * 反回上一个fragment并且携带参数
     *
     * @param bundle
     */
    public void goback(Bundle bundle) {
        SwitchHelper.goBack(this, bundle);
    }

    /**
     * 根据fragment名字切换
     *
     * @param fragmentName
     * @param extras
     */
    public void switchFragment(String fragmentName, Bundle extras) {
        SwitchHelper.switchFragment(container.getActivity(),this, fragmentName, extras);
    }

    /**
     * 根据类名切换
     *
     * @param fragmentClass
     * @param extras
     */
    public void switchFragment(Class<? extends Fragment> fragmentClass, Bundle extras) {
        SwitchHelper.switchFragment(container.getActivity(),this, fragmentClass, extras);
    }

    /**
     * 根据intent进行切换
     *
     * @param intent
     * @param useDefaultFragment
     */
    public void switchFragment(Intent intent, boolean useDefaultFragment) {
        SwitchHelper.switchFragment(container.getActivity(),this, intent, useDefaultFragment);
    }
}
