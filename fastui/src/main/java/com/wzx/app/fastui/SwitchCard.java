package com.wzx.app.fastui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.Pair;

import com.wzx.app.fastui.utils.ComnUtil;

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

    private FragmentSwitcher curSwitcher;

    protected SwitchCard(FragmentSwitcher switcher) {
        this(switcher.getContainer().getActivity());
        this.curSwitcher = switcher;
    }

    protected SwitchCard(FragmentActivity curActivity) {
        this.curActivity = curActivity;
        useAnim = true;
    }

    public SwitchCard target(Intent intent) {
        Pair<String, Bundle> targetFromIntent = ComnUtil.getTargetFromIntent(intent);
        return target(targetFromIntent.first, targetFromIntent.second);
    }

    public SwitchCard target(String fragmentName, Bundle bundle) {
        return target(ComnUtil.loadClass(getClass().getClassLoader(), fragmentName), bundle);
    }

    public SwitchCard target(Class<? extends SwitchFragment> fragmentClass, Bundle bundle) {
        return target(ComnUtil.newInstance(fragmentClass), bundle);
    }

    public SwitchCard target(SwitchFragment fragment, Bundle bundle) {
        targetFragment = fragment;
        targetBundle = bundle;
        return this;
    }

    public SwitchCard goback(){
        return goback(null);
    }

    public SwitchCard goback(Bundle bundle){
        targetFragment = curSwitcher.getContainer().getSwitchLastFragment();
        targetBundle = bundle;
        return this;
    }

    boolean isUseAnim(){
        return useAnim;
    }

    String getHostName() {
        String hostName = targetFragment.hostName();
        return hostName == null ? curActivity.getClass().getName() : hostName;
    }

    FragmentSwitcher getCurSwitcher(){
        return curSwitcher;
    }

    FragmentActivity getCurActivity() {
        return curActivity;
    }

    SwitchFragment getTargetFragment() {
        return targetFragment;
    }

    void setTargetFragment(SwitchFragment fragment){
        targetFragment = fragment;
    }

    int getEnterAnim() {
        return enterAnim;
    }

    int getExitAnim(){
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

    boolean isFinishCurrent(){
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
