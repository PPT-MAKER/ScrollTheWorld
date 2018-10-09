package com.bignerdranch.android.scrolltheworld.common.activity;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bignerdranch.android.scrolltheworld.common.Utils.NetReceiver;
import com.bignerdranch.android.scrolltheworld.common.observer.NetObserver;
import java.util.List;
import java.util.Stack;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huangwt on 2018/10/9
 */

public abstract class BaseActivity extends AppCompatActivity implements NetObserver {

    public static Stack<Activity> mActivityStack = new Stack<Activity>();

    protected boolean isPaused = false;

    public static boolean isActive;

    protected Context mContext;

    Unbinder binder;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        NetReceiver.registerObsover(this);
        mActivityStack.push(this);
        isPaused = false;
        setViewLayout();
        ButterKnife.bind(this);
    }

    protected void setViewLayout() {
        setContentView(getContentViewResId());
    }

    protected abstract int getContentViewResId();

    public void callbackNetStatus(int netStatus) {
        showToast(NetReceiver.getNetStatusName(netStatus));
    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private Handler mHandler = new Handler();

    @Override
    protected void onResume() {
        super.onResume();
        isPaused = false;
        if (!isActive) {
            onResumeActionFromBack();
            isActive = true;//程序从后台唤醒
        }
    }

    protected void onResumeActionFromBack() {
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        isPaused = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;
    }

    @Override
    protected void onStop() {
        if (!isAppOnForeground()) {
            isActive = false;//程序进入后台
            doActionInBack();
        }
        super.onStop();
    }

    protected void doActionInBack() {

    }

    @Override
    public void finish() {
        mActivityStack.remove(this);
        super.finish();
    }

    protected void onDestroy() {
        super.onDestroy();
        NetReceiver.unregisterObsover(this);
    }

    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    protected void clearActivityStack(Stack<Activity> stack) {
        for (int i = 0; i < stack.size(); i++) {
            stack.get(i).finish();
        }
    }

    protected void showToast(String message) {
        if (isPaused){
            return;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public boolean isNetworkError() {
        return NetReceiver.isNetworkError(this);
    }

    public boolean isMobile() {
        return NetReceiver.isMobile(this);
    }

    public boolean isWifi() {
        return NetReceiver.isWifi(this);
    }

    @Override
    public String getLocalClassName(){
        return null;
    }
}
