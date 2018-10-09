package com.bignerdranch.android.scrolltheworld.common.Utils;

/**
 * Created by huangwt on 2018/10/9
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bignerdranch.android.scrolltheworld.base.BaseApp;
import com.bignerdranch.android.scrolltheworld.common.observer.NetObserver;

import java.util.ArrayList;

public class NetReceiver extends BroadcastReceiver {

    private static ArrayList<NetObserver> list = new ArrayList<>();

    public static int DISCONNECTED = 0;

    private static int WIFI = 1;

    private static int MOBILE = 2;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null != intent
                && ConnectivityManager.CONNECTIVITY_ACTION.equals(intent
                .getAction())) {
            // 通知各个观察者
            if (ListUtil.isEmpty(list))
            {
                return;
            }
            int netStatus = checkNetStatus(context);
            for (NetObserver netObserver : list) {
                netObserver.callbackNetStatus(netStatus);
            }
        }
    }

    public static void registerObsover(NetObserver netObserver) {
        list.add(netObserver);
    }

    public static void unregisterObsover(NetObserver netObserver) {
        list.remove(netObserver);
    }

    public static boolean isNetworkError(Context context) {
        return getNetStatus(context) == DISCONNECTED;
    }

    public static boolean isMobile(Context context) {
        return getNetStatus(context) == MOBILE;
    }

    public static boolean isWifi(Context context) {
        return getNetStatus(context) == WIFI;
    }

    public static int getNetStatus(Context context) {
        return checkNetStatus(context);
    }

    private static int checkNetStatus(Context context) {
        if(context==null){
            context= BaseApp.getApplicationContext();
        }
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null || manager.getActiveNetworkInfo() == null) {
            return DISCONNECTED;
        }
        NetworkInfo activeNet = manager.getActiveNetworkInfo();
        int curState = DISCONNECTED;
        if (activeNet != null && activeNet.isAvailable() && activeNet.isConnected()) {
            int netType = activeNet.getType();
            switch (netType) {
                case ConnectivityManager.TYPE_MOBILE:
                    curState = MOBILE;
                    break;
                case ConnectivityManager.TYPE_WIFI:
                    curState = WIFI;
                    break;
                default:
                    curState = DISCONNECTED;
                    break;
            }
        }
        return curState;
    }

    public static String getNetStatusName(int netStatus) {
        if (DISCONNECTED == netStatus) {
            return "当前无网络";
        } else if (MOBILE == netStatus) {
            return "当前网络:2G/3G/4G";
        } else {
            return "当前网络:wifi";
        }
    }
}
