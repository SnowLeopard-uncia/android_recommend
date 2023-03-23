package com.zyw.recommend_system;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * 获取全局Context
 * 调用方法: MyApplication.context
 */

public class MyApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

}