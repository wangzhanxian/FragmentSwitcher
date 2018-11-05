package com.wzx.app.smartui;

import com.wzx.app.smartui.fragments.MainFragment;

@LayoutId(R.layout.activity_main)
public class MainActivity extends BaseActivity{

    @Override
    protected String getDefaultFragmentName() {
        return MainFragment.class.getName();
    }
}
