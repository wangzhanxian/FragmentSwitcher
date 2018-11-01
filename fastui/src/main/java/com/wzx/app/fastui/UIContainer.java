package com.wzx.app.fastui;


import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.wzx.app.fastui.utils.ComnUtil;

import java.util.LinkedList;

public class UIContainer {

    private FragmentActivity activity;

    private LinkedList<Fragment> stack;

    private String defaultFragmentName;

    private @IdRes
    int containerId;


    public UIContainer(FragmentActivity activity) {
        this.activity = activity;
        this.stack = new LinkedList<>();
    }

    FragmentActivity getActivity() {
        return activity;
    }

    String getDefaultFragmentName() {
        return defaultFragmentName;
    }

    int getContainerId() {
        return containerId;
    }

    UIContainer setDefaultFragmentName(String defaultFragmentName) {
        this.defaultFragmentName = defaultFragmentName;
        return this;
    }

    UIContainer setContainerId(int containerId) {
        this.containerId = containerId;
        return this;
    }

    FragmentManager getFragmentManager() {
        return activity.getSupportFragmentManager();
    }


    Fragment getFirstFragment() {
        return getStackSize() != 0 ? stack.getFirst() : null;
    }

    Fragment getCurFragment() {
        return getStackSize() > 0 ? stack.getLast() : null;
    }


    Fragment getSwitchLastFragment() {
        return getStackSize() > 1 ? stack.get(getStackSize() - 2) : null;
    }


    int getStackSize() {
        return stack.size();
    }

    Fragment isSwitchLast(Fragment fragment) {
        boolean reuseTask = ComnUtil.getLaunchMode(fragment) == LaunchMode.SINGLETASK;
        for (Fragment stackFragment : stack) {
            if (stackFragment == fragment || (reuseTask && TextUtils.equals(stackFragment.getClass().getName(), fragment.getClass().getName()))) {
                return stackFragment;
            }
        }
        return null;
    }

    UIContainer addToStack(Fragment targetFragment, FragmentTransaction transaction) {
        stack.add(targetFragment);
        if (!targetFragment.isAdded()) {
            transaction.add(getContainerId(), targetFragment);
        } else {
            transaction.show(targetFragment);
        }
        return this;
    }

    UIContainer popStack(Fragment targetFragment, FragmentTransaction transaction) {
        while (getStackSize() > 0) {
            Fragment last = stack.getLast();
            if (last != targetFragment) {
                stack.removeLast();
                transaction.remove(last);
            } else {
                transaction.show(targetFragment);
                break;
            }
        }
        return this;
    }

    UIContainer removeStack(Fragment fragment) {
        stack.remove(fragment);
        return this;
    }

}
