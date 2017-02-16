package com.zxy.functioncollection;

import android.app.Application;

/**
 * Created by Administrator on 2017/2/14 0014.
 */

public class MyApplication extends Application {
    private static MyApplication sInstance;

    public MyApplication() {
        sInstance = MyApplication.this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static MyApplication getInstance() {
        return sInstance;
    }
}
