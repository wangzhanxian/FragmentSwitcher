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

import com.wzx.app.fastui.utils.FragmentUtil;

import java.util.List;

/**
 * Describe ：自定义FrameLayout
 */
public class ContainerView extends FrameLayout {

    private ContainerManager container;

    private boolean isAttached;

    public ContainerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ContainerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        String defalutFragmentName;
        TypedArray typedArray = null;
        try {
            typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ContainerView);
            //获取默认fragment类名
            defalutFragmentName = typedArray.getString(R.styleable.ContainerView_defaultFragmentName);
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
        container = new ContainerManager((FragmentActivity) getContext()).setContainerId(getId()).setDefaultFragmentName(defalutFragmentName);
        ContainerCollector.add(container);
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
        ContainerCollector.remove(container);
    }

    ContainerManager getContainer() {
        return container;
    }


    public ContainerView setDefalutFragmentName(String defalutFragmentName) {
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
            if (!tryShowRestoreStackFragment()) {
                Intent intent = container.getActivity().getIntent();
                if (FragmentUtil.hasTarget(intent)) {
                    SwitchHelper.with(container.getActivity()).target(intent).commit();
                } else if (container.getDefaultFragmentName() != null) {
                    SwitchHelper.with(container.getActivity()).target(container.getDefaultFragmentName(), null).commit();
                }
            }
        }
    }

    /**
     * 尝试显示被保存的Fragment
     * v4-24.0.0+ 开始，官方修复了上述 没有保存mHidden的问题，所以如果你在使用24.0.0+的v4包，下面分析的2个解决方案可以自行跳过...
     * 参考文档：https://www.jianshu.com/p/d9143a92ad94
     * @return
     */
    private boolean tryShowRestoreStackFragment(){
        List<Fragment> fragments = container.getFragmentManager().getFragments();
        if (fragments !=null && fragments.size()>0){
            for(int i= 0;i<fragments.size();i++){
                Fragment fragment = fragments.get(i);
                if (fragment instanceof SwitchFragment){
                    container.addToStack((SwitchFragment) fragment);
                }
            }
        }
        return container.getStackSize()>0;
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
        return goback(bundle, true);
    }

    public boolean goback(Bundle bundle, boolean useAnim) {
        if (container.getSwitchLastFragment() != null) {
            return SwitchHelper.with(container.getActivity()).target(container.getSwitchLastFragment(), bundle).animEnable(useAnim).commit();
        }
        return false;
    }
}
