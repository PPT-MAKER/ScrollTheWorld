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

    private static int mLastTagHash = -1;

    private static AtomicBoolean isRunning;

    public final static int FILTER_BY_ID = 1;

    public final static int FILTER_BY_TAG = 2;

    public static long sSystemTimeInterval = 20L;

    public enum Order{
        FIRST, LAST
    }

    public static boolean isClickAble(View v, SingleClick singleClick) {
        int viewId = v.getId();
        Object viewTag = v.getTag();
        long time = System.currentTimeMillis();
        if (singleClick.filterType() == FILTER_BY_TAG && null != viewTag) {
            if (time - mLastClickTime <singleClick.timeValue() && viewTag.hashCode() != mLastTagHash) {
                return false;
            }
            mLastClickTime = time;
            mLastTagHash = viewTag.hashCode();
            return true;
        } else {
            if (time - mLastClickTime < singleClick.timeValue() && viewId == mLastClickViewId) {
                return false;
            }
            mLastClickTime = time;
            mLastClickViewId = viewId;
            return true;
        }
    }

    public static boolean isClickAbleLast(View view, SingleClick singleClick) {
        if (singleClick.filterType() == FILTER_BY_ID) {

        }
    }

    public static void setClickTime(long timeStamp) {
        mLastClickTime = timeStamp;
    }

    public static long getClickTime() {
        return mLastClickTime;
    }
}
