package com.bignerdranch.android.scrolltheworld.common.Utils;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bignerdranch.android.scrolltheworld.common.activity.BaseActivity;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Created by huangwt on 2018/10/10
 */

@Aspect
public class ClickAspect {

    @Pointcut("execution(@com.bignerdranch.android.scrolltheworld.common.Utils.SingleClick * *(..))")
    public void methodAnnotated() {}

    @Around("methodAnnotated()")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出方法的参数
        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) {
                view = (View) arg;
                break;
            }
        }
        if (view == null) {
            return;
        }
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if (!method.isAnnotationPresent(SingleClick.class)) {
            return;
        }
        if (method.isAnnotationPresent(CheckNetError.class)) {
            if (NetReceiver.isNetworkError(null)) {
                Toast.makeText(BaseActivity.mActivityStack.get(0), "当前网络出错", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        SingleClick singleClick = method.getAnnotation(SingleClick.class);
        if (singleClick.order() == BanClickUtil.Order.FIRST) {
            if (BanClickUtil.isClickAble(view, singleClick.timeValue(), singleClick.tag())) {
                joinPoint.proceed();
            }
        } else{

        }
    }

}
