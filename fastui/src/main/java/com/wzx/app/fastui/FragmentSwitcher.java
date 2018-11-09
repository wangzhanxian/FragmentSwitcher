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

import com.wzx.app.fastui.utils.ComnUtil;


public class FragmentSwitcher extends FrameLayout {


    private UIContainer container;

    private boolean isAttached;

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
        SwitchContainerManager.add(container);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isAttached = true;
        tryShowDefaultFragment();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isAttached = false;
        SwitchContainerManager.remove(container);
    }

    UIContainer getContainer() {
        return container;
    }


    public FragmentSwitcher setDefalutFragmentName(String defalutFragmentName) {
        if (container.getDefaultFragmentName() != null) {
            throw new RuntimeException("the container has set defalutFragmentName");
        }
        if (defalutFragmentName != null) {
            container.setDefaultFragmentName(defalutFragmentName);
            tryShowDefaultFragment();
        }
        return this;
    }


    private void tryShowDefaultFragment() {
        if (isAttached && container.getStackSize() == 0) {
            Intent intent = container.getActivity().getIntent();
            if (ComnUtil.hasTarget(intent)) {
                SwitchHelper.with(container.getActivity()).target(intent).commit();
            } else if (container.getDefaultFragmentName() != null) {
                SwitchHelper.with(container.getActivity()).target(container.getDefaultFragmentName(), null).commit();
            }
        }
    }

    public Fragment getCurFragment() {
        return container.getCurFragment();
    }


    public boolean canGoback() {
        return container.getStackSize() > 1;
    }

    public boolean goback() {
        return goback(null);
    }

    public boolean goback(Bundle bundle) {
        return goback(bundle,true);
    }

    public boolean goback(Bundle bundle, boolean useAnim) {
        return SwitchHelper.with(this).goback( bundle).animEnable(useAnim).commit();
    }
}
