package com.wzx.app.fastui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Pair;

import com.wzx.app.fastui.utils.ComnUtil;

public class SwitchHelper {

    private static Handler handler = new Handler(Looper.getMainLooper());
    /**
     * 通过intent切换
     *
     * @param activity
     * @param containerView
     * @param intent
     * @param useDefaultFragment
     */
    public static void switchFragment(FragmentActivity activity, FragmentSwitcher containerView, Intent intent, boolean useDefaultFragment) {
        Pair<String, Bundle> bundlePair = ComnUtil.getTargetFromIntent(intent);
        String targetFragmentName = bundlePair.first;
        if (bundlePair.first == null && useDefaultFragment && containerView != null) {
            targetFragmentName = containerView.getContainer().getDefaultFragmentName();
        }
        Class fragmentClass = ComnUtil.loadClass(activity.getClassLoader(), targetFragmentName);
        if (fragmentClass != null && checkHostAndOpen(activity, fragmentClass, bundlePair.second) && containerView != null && containerView.checkCanSwitch()) {
            doSwitch(containerView, fragmentClass, bundlePair.second, useDefaultFragment);
        }
    }

    /**
     * 直接通过类名切换
     *
     * @param activity
     * @param containerView
     * @param fragmentName
     * @param extras
     */
    public static void switchFragment(FragmentActivity activity, FragmentSwitcher containerView, String fragmentName, Bundle extras) {
        switchFragment(activity, containerView, ComnUtil.loadClass(activity.getClassLoader(), fragmentName), extras);
    }

    public static void switchFragment(FragmentActivity activity, FragmentSwitcher containerView, Class<? extends Fragment> fragmentClass, Bundle extras) {
        if (fragmentClass != null && checkHostAndOpen(activity, fragmentClass, extras) && containerView != null && containerView.checkCanSwitch()) {
            doSwitch(containerView, fragmentClass, extras, true);
        }
    }


    private static void doSwitch(FragmentSwitcher containerView, Class<? extends Fragment> fragmentClass, Bundle extras, boolean useAnim) {
        if (fragmentClass != null) {
            doSwitch(containerView.getContainer(), ensureFragment(fragmentClass), extras, useAnim);
        }
    }

    private static void doSwitch(UIContainer container, Fragment targetFragment, Bundle extras, boolean useAnim) {
        if (targetFragment != null) {
            Fragment stackFragment = container.isSwitchLast(targetFragment);
            boolean switchLast = stackFragment != null;
            if (switchLast) {
                //如果目标fragment就在栈顶
                if (container.getCurFragment() == stackFragment) {
                    return;
                }
                targetFragment = stackFragment;
            }
            // 添加参数
            if (extras != null) {
                targetFragment.setArguments(extras);
            }
            FragmentTransaction transaction = container.getFragmentManager().beginTransaction();
            if (container.getStackSize() != 0 && useAnim) {
                if (!switchLast) {
                    transaction.setCustomAnimations(R.anim.anim_enter_right_to_left, R.anim.anim_exit_right_to_left);
                } else {
                    transaction.setCustomAnimations(R.anim.anim_enter_left_to_right, R.anim.anim_exit_left_to_right);
                }
            }
            hideAllFragment(transaction, container, switchLast);
            if (!switchLast) {
                container.addToStack(targetFragment, transaction);
            } else {
                container.popStack(targetFragment, transaction);
            }
            transaction.commit();
        }
    }

    private static void hideAllFragment(FragmentTransaction transaction, UIContainer container, boolean switchLast) {
        for (Fragment fragment : container.getFragmentManager().getFragments()) {
            if (!fragment.isHidden()) {
                if (switchLast && fragment == container.getCurFragment()) {
                    continue;
                }
                transaction.hide(fragment);
            }
        }
    }

    private static Fragment ensureFragment(Class<? extends Fragment> fragmentClass) {
        try {
            return fragmentClass.newInstance();
        } catch (Exception e) {
            return null;
        }
    }


    public static boolean checkHostAndOpen(FragmentActivity activity, String targetFragmentName, Bundle extras) {
        return checkHostAndOpen(activity, ComnUtil.loadClass(activity.getClassLoader(), targetFragmentName), extras);
    }

    private static boolean checkHostAndOpen(FragmentActivity activity, Class fragmentClass, Bundle extras) {
        if (fragmentClass == null) {
            return false;
        }
        Class<? extends FragmentActivity> hostActClass = ComnUtil.getContainerActivity(fragmentClass);
        if (hostActClass == null) {
            hostActClass = activity.getClass();
        }
        boolean isCurHost = TextUtils.equals(hostActClass.getName(), activity.getClass().getName());
        if (!isCurHost) {
            Intent intent = new Intent(activity, hostActClass);
            activity.startActivity(ComnUtil.setTargetToIntent(intent, fragmentClass.getName(), extras));
        }
        return isCurHost;
    }


    public static void goBack(FragmentSwitcher containerView, Bundle bundle) {
        goBack(containerView,bundle,true);
    }
    public static void goBack(FragmentSwitcher containerView, Bundle bundle,boolean useAnim) {
        if (containerView == null || !containerView.checkCanSwitch()) {
            return;
        }
        UIContainer uiContainer = containerView.getContainer();
        if (uiContainer != null) {
            Fragment switchLastFragment = uiContainer.getSwitchLastFragment();
            if (switchLastFragment != null) {
                doSwitch(uiContainer, switchLastFragment, bundle, useAnim);
            } else {
                uiContainer.getActivity().finish();
            }
        }

    }

    /**由于动画需要执行时间所以需要延时处理
     * @param containerView
     * @param fragment
     */
    public static void finish(FragmentSwitcher containerView, final Fragment fragment) {
        if (fragment == null || containerView == null || !containerView.checkCanSwitch()) {
            return;
        }
        //删除对象和栈记录
        final UIContainer container = containerView.getContainer();
        //是否删除当前显示的Fragment
        boolean removeCurShow = fragment == container.getCurFragment();
        if (removeCurShow) {
            goBack(containerView,null,true);
        }else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    container.removeStack(fragment);
                    FragmentTransaction transaction = container.getFragmentManager().beginTransaction();
                    transaction.remove(fragment).commit();
                }
            },200);

        }
    }
}
