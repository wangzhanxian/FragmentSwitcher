package com.wzx.app.smartui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wzx.app.fastui.LaunchMode;
import com.wzx.app.fastui.annotions.Host;
import com.wzx.app.fastui.annotions.Mode;
import com.wzx.app.smartui.AActivity;
import com.wzx.app.smartui.LayoutId;
import com.wzx.app.smartui.R;


@Mode(LaunchMode.STANDRAD)
@LayoutId(R.layout.fragment_a1)
public class A1Fragment extends BaseFragment {

    @Override
    protected void bindViews() {
        super.bindViews();
        if (getArguments() != null) {
            String string = getArguments().getString("tag");
            Log.e("BaseFragment", string + "");
        }

        findViewById(R.id.tv_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("tag", "A1 fff");
                mActivity.switchFragment(A2Fragment.class, bundle);
                mActivity.finish(A1Fragment.this);
            }
        });
    }
}
