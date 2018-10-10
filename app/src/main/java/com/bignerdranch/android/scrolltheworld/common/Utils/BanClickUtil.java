package com.bignerdranch.android.scrolltheworld.common.Utils;

import android.view.View;

import org.aspectj.lang.reflect.MethodSignature;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by huangwt on 2018/10/10
 */
public class BanClickUtil {

    private static long mLastClickTime = 0;

    private static int mLastClickViewId = -1;

    private static int mLastViewTag;

    private static AtomicBoolean isRunning;


    enum Order{
        FIRST, LAST
    }

    public static boolean isClickAble(View v, long timestamp, int tag) {
        int viewId = v.getId();
        long time = System.currentTimeMillis();
        if (time - mLastClickTime < timestamp && viewId == mLastClickViewId) {
            return false;
        }
        mLastClickTime = time;
        mLastClickViewId = viewId;
        return true;
    }

}
