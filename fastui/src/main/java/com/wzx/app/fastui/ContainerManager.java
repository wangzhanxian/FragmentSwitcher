package com.wzx.app.fastui;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.wzx.app.fastui.utils.FragmentUtil;

import java.util.LinkedList;

/**
 * Describe ：容器管理者
 */
public class ContainerManager {

    private FragmentActivity activity;

    private LinkedList<SwitchFragment> stack;

    private String defaultFragmentName;

    private @IdRes
    int containerId;


    public ContainerManager(FragmentActivity activity) {
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

    ContainerManager setDefaultFragmentName(String defaultFragmentName) {
        this.defaultFragmentName = defaultFragmentName;
        return this;
    }

    ContainerManager setContainerId(int containerId) {
        this.containerId = containerId;
        return this;
    }

    FragmentManager getFragmentManager() {
        return activity.getSupportFragmentManager();
    }


    SwitchFragment getFirstFragment() {
        return getStackSize() != 0 ? stack.getFirst() : null;
    }

    SwitchFragment getCurFragment() {
        return getStackSize() > 0 ? stack.getLast() : null;
    }


    SwitchFragment getSwitchLastFragment() {
        return getStackSize() > 1 ? stack.get(getStackSize() - 2) : null;
    }


    int getStackSize() {
        return stack.size();
    }

    SwitchFragment isSwitchLast(SwitchFragment fragment) {
        boolean reuseTask = FragmentUtil.getLaunchMode(fragment) == LaunchMode.SINGLETASK;
        for (SwitchFragment stackFragment : stack) {
            if (stackFragment == fragment || (reuseTask && TextUtils.equals(stackFragment.getClass().getName(), fragment.getClass().getName()))) {
                return stackFragment;
            }
        }
        return null;
    }

    ContainerManager addToStack(SwitchFragment targetFragment) {
        stack.add(targetFragment);
        return this;
    }

    ContainerManager addToStack(SwitchFragment targetFragment, FragmentTransaction transaction) {
        addToStack(targetFragment);
        if (!targetFragment.isAdded()) {
            transaction.add(getContainerId(), targetFragment);
        } else {
            transaction.show(targetFragment);
        }
        return this;
    }

    ContainerManager popStack(SwitchFragment targetFragment, FragmentTransaction transaction) {
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

    ContainerManager removeStack(SwitchFragment fragment, FragmentTransaction transaction) {
        stack.remove(fragment);
        transaction.remove(fragment);
        return this;
    }

    ContainerManager hideStack(SwitchFragment fragment, FragmentTransaction transaction) {
        transaction.hide(fragment);
        return this;
    }
}
