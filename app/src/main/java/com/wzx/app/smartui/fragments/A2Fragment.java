package com.wzx.app.smartui.fragments;

import android.util.Log;
import android.view.View;

import com.wzx.app.fastui.annotions.Host;
import com.wzx.app.smartui.AActivity;
import com.wzx.app.smartui.LayoutId;
import com.wzx.app.smartui.R;

@LayoutId(R.layout.fragment_a2)
public class A2Fragment extends BaseFragment {
    @Override
    protected void bindViews() {
        super.bindViews();
        if (getArguments() != null){
            String string = getArguments().getString("tag");
            Log.e("BaseFragment",string+"");
        }

        findViewById(R.id.tv_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.switchFragment(A1Fragment.class,null);
            }
        });
    }
}
