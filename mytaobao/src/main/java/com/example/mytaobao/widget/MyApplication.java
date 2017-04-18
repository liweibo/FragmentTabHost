package com.example.mytaobao.widget;

import android.app.Application;
import android.content.Context;

/**
 * Created by lwb on 2017/4/17.
 */

public class MyApplication extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

    }
    public static Context getContext(){
        return mContext;

    }
}
