package com.wzx.app.fastui;

import android.support.v4.app.FragmentActivity;

import java.util.HashMap;

public class ContainerCollector {

    private static HashMap<FragmentActivity,ContainerManager> containers = new HashMap<>();

    static void add(ContainerManager container){
        containers.put(container.getActivity(),container);
    }

    static void remove(ContainerManager container){
        containers.remove(container.getActivity());
    }

    static ContainerManager getUIContainer(FragmentActivity activity){
        return containers.get(activity);
    }

}
