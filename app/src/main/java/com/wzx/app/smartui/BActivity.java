package com.wzx.app.smartui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wzx.app.smartui.fragments.B1Fragment;
import com.wzx.app.smartui.fragments.MainFragment;

@LayoutId(R.layout.activity_b)
public class BActivity extends BaseActivity {

    @Override
    protected String getDefaultFragmentName() {
        return B1Fragment.class.getName();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.tv_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(MainFragment.class,null);
            }
        });
    }
}
