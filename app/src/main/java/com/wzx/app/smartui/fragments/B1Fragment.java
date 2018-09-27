package com.wzx.app.smartui.fragments;

import android.view.View;

import com.wzx.app.fastui.annotions.Host;
import com.wzx.app.smartui.LayoutId;
import com.wzx.app.smartui.MainActivity;
import com.wzx.app.smartui.R;

@Host(MainActivity.class)
@LayoutId(R.layout.fragment_b1)
public class B1Fragment extends BaseFragment {

    @Override
    protected void bindViews() {
        super.bindViews();
        findViewById(R.id.tv_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.switchFragment(MainFragment.class,null);
            }
        });
    }
}
