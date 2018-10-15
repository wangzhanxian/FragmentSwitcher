package com.wzx.app.fastui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.wzx.app.fastui.utils.ComnUtil;

import java.util.HashMap;

public class SwitchHelper {

    private static HashMap<FragmentSwitcher,UIContainer> map = new HashMap<>();

    public static final String FRAGMENT_CLASS_NAME = "fragment_class_name";
    public static final String FRAGMENT_EXTRA_BUNDLE = "fragment_extra_bundle";


    static UIContainer get(FragmentSwitcher containerView){
        return map.get(containerView);
    }

    static void put(FragmentSwitcher containerView, UIContainer container){
        map.put(containerView,container);
    }

    static void remove(FragmentSwitcher containerView){
        map.remove(containerView);
    }


    private static UIContainer ensureContainer(FragmentSwitcher containerView) {
        UIContainer container = get(containerView);
        if (container == null) {
            container = containerView.getContainer();
            put(containerView, container);
        }
        return container;
    }

    public static void switchFragment(FragmentSwitcher containerView, Intent intent, boolean useDefaultFragment) {
        if (!containerView.checkCanSwitch()){
            return;
        }
        UIContainer container = ensureContainer(containerView);
        Bundle bundle = intent.getExtras();
        String fragmentName = null;
        Bundle fragmentArgs = null;
        if (bundle != null) {
             fragmentName = bundle.getString(FRAGMENT_CLASS_NAME);
             fragmentArgs = bundle.getBundle(FRAGMENT_EXTRA_BUNDLE);
        }
        if (fragmentName == null && useDefaultFragment) {
            fragmentName = container.getDefaultFragmentName();
        }
        doSwitch(container,fragmentName,fragmentArgs,false,true,useDefaultFragment);
    }

    public static void switchFragment(FragmentSwitcher containerView, String fragmentName, Bundle extras){
        if (!containerView.checkCanSwitch()){
            return;
        }
        doSwitch(ensureContainer(containerView),fragmentName,extras,false,true,true);
    }


    private static void doSwitch(UIContainer container, String fragmentName, Bundle extras,boolean switchLast,boolean reuseStack,boolean useAnim) {
        if (fragmentName != null) {
            Fragment fragment = ensureFragment(container.getActivity(),fragmentName);
            if (fragment != null && checkHostAndOpen(container.getActivity(),fragment.getClass(),extras)) {
                // 添加参数
                if (extras != null) {
                    fragment.setArguments(extras);
                }
                //如果目标fragment就在栈顶
                if (container.getCurFragment() == fragment){
                    return;
                }
                FragmentTransaction transaction = container.getFragmentManager().beginTransaction();
                if (container.getStackSize() != 0 && useAnim) {
                    if (!switchLast) {
                        transaction.setCustomAnimations(R.anim.anim_enter_right_to_left, R.anim.anim_exit_right_to_left);
                    }else {
                        transaction.setCustomAnimations(R.anim.anim_enter_left_to_right, R.anim.anim_exit_left_to_right);
                    }
                }
                hideAllFragment(transaction,container,switchLast);
                if (!fragment.isAdded()) {
                    transaction.add(container.getContainerId(), fragment, fragmentName);
                } else {
                    transaction.show(fragment);
                }
                if (!switchLast) {
                    container.addToStack(fragment,reuseStack);
                }else {
                    container.popStack();
                }
                transaction.commit();
                container.setCurFragment(fragment);
            }
        }
    }

    private static void hideAllFragment(FragmentTransaction transaction, UIContainer container, boolean switchLast){
        Fragment curFragment = null;
        if (switchLast){
            curFragment = container.getCurFragment();
        }
        for (Fragment fragment:container.getFragmentManager().getFragments()) {
            if (fragment != null && fragment == curFragment){
                transaction.remove(curFragment);
            }else if (!fragment.isHidden()){
                transaction.hide(fragment);
            }
        }
    }

   static Fragment ensureFragment(FragmentActivity activity,String fragmentName){
        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(fragmentName);
        if (fragment == null) {
            try {
                fragment = (Fragment) ComnUtil.loadClass(activity.getClassLoader(),fragmentName).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fragment;
    }


    public static boolean checkHostAndOpen(FragmentActivity activity, String targetFragmentName, Bundle extras){
        return checkHostAndOpen(activity,ComnUtil.loadClass(activity.getClassLoader(), targetFragmentName),extras);

    }
    public static boolean checkHostAndOpen(FragmentActivity activity, Class fragmentClass, Bundle extras){
        if (fragmentClass ==null){
            return false;
        }
        Class<? extends FragmentActivity> hostActClass = ComnUtil.getContainerActivity(fragmentClass);
        if (hostActClass == null){
            hostActClass = activity.getClass();
        }
        boolean isCurHost = TextUtils.equals(hostActClass.getName(),activity.getClass().getName());
        if (!isCurHost){
            Intent intent = new Intent(activity,hostActClass);
            intent.putExtra(FRAGMENT_CLASS_NAME, fragmentClass.getName());
            if (extras != null){
                intent.putExtra(FRAGMENT_EXTRA_BUNDLE, extras);
            }
            activity.startActivity(intent);
        }
        return isCurHost;
    }


    public static void goBack(FragmentSwitcher containerView, Bundle bundle){
        if (!containerView.checkCanSwitch()){
            return;
        }
        UIContainer uiContainer = get(containerView);
        if (uiContainer != null){
            Fragment switchLastFragment = uiContainer.getSwitchLastFragment();
            if (switchLastFragment != null){
                doSwitch(uiContainer,switchLastFragment.getClass().getName(),bundle,true,true,true);
            }else {
                uiContainer.getActivity().finish();
            }
        }

    }
}
