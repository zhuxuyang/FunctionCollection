package com.zxy.functioncollection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zxy.functioncollection.manager.ActivityManager;

/**
 * Created by Administrator on 2017/2/14 0014.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initListener();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);

        initView();
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().removeActivity(this);
    }
}
