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
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by huangwt on 2018/10/10
 */

@Aspect
public class ClickAspect {

    private static ProceedingJoinPoint sJoinPoint;

    private static ObservableEmitter<Object> sEmitter;

    private static Disposable mDisposable =  Observable.create(new ObservableOnSubscribe<Object>() {
        @Override
        public void subscribe(ObservableEmitter<Object> e) {
            sEmitter = e;
        }
    }).throttleLast(2000L, TimeUnit.MILLISECONDS).subscribe(new Consumer() {
        @Override
        public void accept(Object o) throws Exception {
            try {
                if (sJoinPoint != null) {
                    sJoinPoint.proceed();
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    });

    @Pointcut("execution(@com.bignerdranch.android.scrolltheworld.common.Utils.SingleClick * *(..))")
    public void methodAnnotated() {}

    @Around("methodAnnotated()")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint) throws Throwable {
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
            if (BanClickUtil.isClickAble(view, singleClick)) {
                joinPoint.proceed();
            }
        } else{
            if (BanClickUtil.isClickAbleLast(view, singleClick))
            sJoinPoint = joinPoint;
            sEmitter.onNext(new Object());
        }
    }
}
