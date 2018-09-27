package com.wzx.app.smartui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wzx.app.fastui.annotions.Host;
import com.wzx.app.smartui.LayoutId;
import com.wzx.app.smartui.MainActivity;
import com.wzx.app.smartui.R;

@Host(MainActivity.class)
@LayoutId(R.layout.fragment_a1)
public class MainFragment extends BaseFragment {

    TextView tv_text;
    @Override
    protected void bindViews() {
        super.bindViews();
        tv_text = findViewById(R.id.tv_text);
        tv_text.setText("main");
        if (mActivity instanceof MainActivity){
            tv_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("tag","main aaa");
                    mActivity.switchFragment(A1Fragment.class,bundle);
                }
            });
        }
    }
}
