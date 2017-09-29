package com.mynet.interview.mynetapp.Helper;

import android.app.Application;

/**
 * Created by murathas on 29.09.2017.
 */

public class MyApplication extends Application {
    private static MyApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;


    }
    public static synchronized MyApplication getInstance() {
        return mInstance;
    }
}
