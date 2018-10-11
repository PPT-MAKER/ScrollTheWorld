package com.bignerdranch.android.scrolltheworld;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.bignerdranch.android.scrolltheworld.base.BaseApp;
import com.bignerdranch.android.scrolltheworld.common.Utils.NetReceiver;

/**
 * Created by huangwt on 2018/10/9
 */
public class CApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        registerNetReceiver();
        BaseApp.setBaseIApp(this);
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    private void registerNetReceiver() {
        NetReceiver netReceiver = new NetReceiver();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netReceiver, mFilter);
    }
}
