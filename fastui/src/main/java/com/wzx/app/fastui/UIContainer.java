package com.wzx.app.fastui;


import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.LinkedList;

public class UIContainer {

    private FragmentActivity activity;

    private LinkedList<Fragment> stack;

    private String defaultFragmentName;

    private @IdRes int containerId;

    private Fragment curFragment;

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

    FragmentManager getFragmentManager(){
        return activity.getSupportFragmentManager();
    }

    Fragment ensureFragment(String fragmentName){
        Fragment fragment = getFragmentManager().findFragmentByTag(fragmentName);
        if (fragment == null) {
            try {
                fragment = (Fragment) activity.getClassLoader().loadClass(fragmentName).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fragment;
    }

    Fragment getFirstFragment(){
        return getStackSize() != 0? stack.getFirst():null;
    }

    Fragment getCurFragment(){
        return curFragment;
    }

    UIContainer setCurFragment(Fragment fragment){
        curFragment = fragment;
        return this;
    }

    Fragment getSwitchLastFragment(){
        return getStackSize() > 1? stack.get(getStackSize()-2):null;
    }


    int getStackSize(){
        return stack.size();
    }

    UIContainer addToStack(Fragment fragment, boolean reuseStack){
        if (reuseStack){
            int index = stack.indexOf(fragment);
            if (index >= 0){
                while (getStackSize()-1 >index){
                    stack.removeLast();
                }
            }else {
                stack.add(fragment);
            }
        }else {
            stack.add(fragment);
        }
        return this;
    }

    UIContainer popStack(){
        if (getStackSize()>0){
            stack.removeLast();
        }
       return this;
    }

}
