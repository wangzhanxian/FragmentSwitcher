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


    /**
     * 可以开始切换标记
     */
    private boolean start;

    private UIContainer container;

    public FragmentSwitcher(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FragmentSwitcher(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        UI.remove(this);
    }

    private void init(AttributeSet attrs) {
        String defalutFragmentName = null;
        TypedArray typedArray = null;
        try {
            typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FragmentSwitcher);
            /**-------------获取默认fragment类名------------*/
            defalutFragmentName = typedArray.getString(R.styleable.FragmentSwitcher_defaultFragmentName);
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


    public FragmentSwitcher start() {
        if (!start) {
            start = true;
            switchFragment(container.getActivity().getIntent(), true);
        }
        return this;
    }

    public UIContainer getContainer() {
        return container;
    }

    public boolean checkCanSwitch() {
        return start;
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
        if (checkCanSwitch()) {
            UI.goBack(this, bundle);
        }
    }

    /**
     * 根据fragment名字切换
     *
     * @param fragmentName
     * @param extras
     */
    public void switchFragment(String fragmentName, Bundle extras) {
        if (checkCanSwitch()) {
            UI.switchFragment(this, fragmentName, extras);
        }
    }

    /**
     * 根据intent进行切换
     *
     * @param intent
     * @param useDefaultFragment
     */
    public void switchFragment(Intent intent, boolean useDefaultFragment) {
        if (checkCanSwitch()) {
            UI.switchFragment(this, intent, useDefaultFragment);
        }
    }
}
