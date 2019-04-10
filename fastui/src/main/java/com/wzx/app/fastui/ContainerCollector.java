package com.wzx.app.fastui;

import android.support.v4.app.FragmentActivity;

import java.util.WeakHashMap;

/**
 * Describe ：容器集合
 */
public class ContainerCollector {

    private static WeakHashMap<FragmentActivity, ContainerManager> containers = new WeakHashMap<>();

    static void add(ContainerManager container) {
        containers.put(container.getActivity(), container);
    }

    static void remove(ContainerManager container) {
        containers.remove(container.getActivity());
    }

    static ContainerManager getContainerManager(FragmentActivity activity) {
        return containers.get(activity);
    }

}
