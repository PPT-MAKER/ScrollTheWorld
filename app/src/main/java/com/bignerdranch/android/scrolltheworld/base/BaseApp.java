package com.bignerdranch.android.scrolltheworld.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by huangwt on 2018/10/9
 */
public class BaseApp {

    private static Application context;

    public static Context getApplicationContext() {
        return context;
    }

    public static void setBaseIApp(Context context) {
        BaseApp.context=(Application)context;
    }
}