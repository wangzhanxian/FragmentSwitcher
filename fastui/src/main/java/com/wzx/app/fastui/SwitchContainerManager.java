package com.wzx.app.fastui;

import android.support.v4.app.FragmentActivity;

import java.util.HashMap;

public class SwitchContainerManager {

    private static HashMap<FragmentActivity,UIContainer> containers = new HashMap<>();

    static void add(UIContainer container){
        containers.put(container.getActivity(),container);
    }

    static void remove(UIContainer container){
        containers.remove(container.getActivity());
    }

    static UIContainer getUIContainer(FragmentActivity activity){
        return containers.get(activity);
    }

}
