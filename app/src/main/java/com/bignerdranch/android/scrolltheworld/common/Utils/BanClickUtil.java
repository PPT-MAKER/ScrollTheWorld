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

    public final static int FILTER_BY_ID = 1;   //To be addded in constant

    public final static int FILTER_BY_TAG = 2;

    public final static int TAG_KEY = -1;

    public enum Order{
        FIRST, LAST
    }

    public static boolean isClickAble(View v, SingleClick singleClick) {
        int viewId = v.getId();
        Object viewTag = v.getTag(TAG_KEY);
        long time = System.currentTimeMillis();
        if (singleClick.filterType() == FILTER_BY_TAG && null != viewTag) {
            if (time - mLastClickTime <singleClick.timeValue() && viewTag.hashCode() == mLastTagHash) {
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
}
