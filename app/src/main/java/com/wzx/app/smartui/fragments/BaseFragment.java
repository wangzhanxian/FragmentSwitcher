package com.wzx.app.smartui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzx.app.fastui.SwitchFragment;
import com.wzx.app.smartui.BaseActivity;
import com.wzx.app.smartui.Utils;

public class BaseFragment extends SwitchFragment {

    protected BaseActivity mActivity;

    protected View mRootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("BaseFragment","hostActivity = "+mActivity.getClass().getSimpleName()+" --fragment = "+getClass().getSimpleName());
        mRootView = inflater.inflate(Utils.getLayoutId(this),null);
        bindViews();
        return mRootView;
    }

    protected void bindViews() {

    }

    @Nullable
    public <T extends View> T findViewById(@IdRes int id) {
        return mRootView.findViewById(id);
    }


    @Override
    public boolean onBackPressed() {
        if (mActivity != null) {
            mActivity.switchToLastFragment();
            return true;
        }
        return super.onBackPressed();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

}
