package com.wzx.app.smartui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.wzx.app.fastui.FragmentSwitcher;
import com.wzx.app.fastui.SwitchHelper;
import com.wzx.app.smartui.fragments.BaseFragment;

@LayoutId(R.layout.activity_base)
public abstract class BaseActivity extends FragmentActivity {

    private FragmentSwitcher fs_switcher;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("BaseFragment","onCreate = "+getClass().getSimpleName());

        setContentView(Utils.getLayoutId(this));
        fs_switcher = findViewById(R.id.fs_switcher);
        if (fs_switcher != null){
            //进行默认的Fragment类名配置
            fs_switcher.setDefalutFragmentName(getDefaultFragmentName());
            //true:可以切换，false：禁用切换
//            fs_switcher.setSwitchEnable(false);
        }
        SwitchHelper.switchFragment(this,fs_switcher,getIntent(),true);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SwitchHelper.switchFragment(this,fs_switcher,intent,false);
        if (fs_switcher != null) {
            fs_switcher.switchFragment(intent, false);
        }
    }

    /**
     * 默认展示的第一个fragment
     */
    protected abstract String getDefaultFragmentName();

    public FragmentSwitcher getSwitcher(){
        return fs_switcher;
    }

    public void switchFragment(@NonNull Class<? extends BaseFragment> targetFragmentClass, Bundle extras) {
        SwitchHelper.switchFragment(this,fs_switcher,targetFragmentClass.getName(),extras);
    }

    public void switchToLastFragment() {
        if (fs_switcher != null) {
            fs_switcher.goback();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (fs_switcher != null) {
            BaseFragment fragment = (BaseFragment) fs_switcher.getCurFragment();
            if (fragment != null) {
                return fragment.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
