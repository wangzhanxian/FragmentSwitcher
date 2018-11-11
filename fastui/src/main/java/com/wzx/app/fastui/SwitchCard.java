package com.wzx.app.fastui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.v4.app.FragmentActivity;
import android.util.Pair;

import com.wzx.app.fastui.utils.FragmentUtil;

/**
 * Describe ：切换参数封装类
 */
public class SwitchCard {

    private FragmentActivity curActivity;

    private SwitchFragment targetFragment;

    private Bundle targetBundle;

    private boolean destoryCurFragment;

    private @AnimatorRes
    @AnimRes
    int enterAnim;

    private @AnimatorRes
    @AnimRes
    int exitAnim;

    /**
     * 是否使用切换动画
     */
    private boolean useAnim;

    private ContainerView curSwitcher;

    protected SwitchCard(ContainerView switcher) {
        this(switcher.getContainer().getActivity());
        this.curSwitcher = switcher;
    }

    protected SwitchCard(FragmentActivity curActivity) {
        this.curActivity = curActivity;
        useAnim = true;
    }

    public SwitchCard target(Intent intent) {
        Pair<String, Bundle> targetFromIntent = FragmentUtil.getTargetFromIntent(intent);
        return target(targetFromIntent.first, targetFromIntent.second);
    }

    public SwitchCard target(String fragmentName, Bundle bundle) {
        return target(FragmentUtil.loadClass(getClass().getClassLoader(), fragmentName), bundle);
    }

    public SwitchCard target(Class<? extends SwitchFragment> fragmentClass, Bundle bundle) {
        return target(FragmentUtil.newInstance(fragmentClass), bundle);
    }

    public SwitchCard target(SwitchFragment fragment, Bundle bundle) {
        targetFragment = fragment;
        targetBundle = bundle;
        return this;
    }

    public SwitchCard goback() {
        return goback(null);
    }

    public SwitchCard goback(Bundle bundle) {
        ContainerManager container = curSwitcher != null ? curSwitcher.getContainer() : ContainerCollector.getUIContainer(curActivity);
        targetFragment = container != null ? container.getSwitchLastFragment() : null;
        targetBundle = bundle;
        return this;
    }

    boolean isUseAnim() {
        return useAnim;
    }

    String getHostName() {
        String hostName = targetFragment.hostName();
        return hostName == null ? curActivity.getClass().getName() : hostName;
    }

    ContainerView getCurSwitcher() {
        return curSwitcher;
    }

    FragmentActivity getCurActivity() {
        return curActivity;
    }

    SwitchFragment getTargetFragment() {
        return targetFragment;
    }

    void setTargetFragment(SwitchFragment fragment) {
        targetFragment = fragment;
    }

    int getEnterAnim() {
        return enterAnim;
    }

    int getExitAnim() {
        return exitAnim;
    }

    Bundle getTargetBundle() {
        return targetBundle;
    }


    /**
     * 是否关闭当前Fragment
     *
     * @return
     */
    public SwitchCard finishCurrent() {
        destoryCurFragment = true;
        return this;
    }

    boolean isFinishCurrent() {
        return destoryCurFragment;
    }

    /**
     * 设置动画
     *
     * @param enter
     * @param exit
     * @return
     */
    public SwitchCard setCustomAnimations(@AnimatorRes @AnimRes int enter, @AnimatorRes @AnimRes int exit) {
        enterAnim = enter;
        exitAnim = exit;
        return this;
    }

    /**
     * @param useAnim
     * @return
     */
    public SwitchCard animEnable(boolean useAnim) {
        this.useAnim = useAnim;
        return this;
    }


    public boolean commit() {
        return SwitchHelper.commit(this);
    }
}
