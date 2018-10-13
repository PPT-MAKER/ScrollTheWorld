package com.bignerdranch.android.scrolltheworld.common.Utils;

import android.view.View;

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
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by huangwt on 2018/10/10
 */

@Aspect
public class ClickAspect {

    private static ObservableEmitter<ProceedingJoinPoint> sEmitter;

    private static Disposable mDisposable;

    private static Consumer<ProceedingJoinPoint> sConsumer = new Consumer<ProceedingJoinPoint>() {
        @Override
        public void accept(ProceedingJoinPoint proceedingJoinPoint) throws Exception {
            try {
                proceedingJoinPoint.proceed();
                if (null != mDisposable && !mDisposable.isDisposed())
                    mDisposable.dispose();    // intervalDuration之后进行销毁;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    };

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
        SingleClick singleClick = method.getAnnotation(SingleClick.class);
        if (singleClick.order() == BanClickUtil.Order.FIRST) {
            if (BanClickUtil.isClickAble(view, singleClick)) {
                joinPoint.proceed();
            }
        } else{
            if (mDisposable == null || mDisposable.isDisposed()) {
                mDisposable = Observable.create(new ObservableOnSubscribe<ProceedingJoinPoint>() {
                    @Override
                    public void subscribe(ObservableEmitter<ProceedingJoinPoint> e) {
                        sEmitter = e;
                    }
                }).throttleLast(singleClick.timeValue(), TimeUnit.MILLISECONDS).subscribe(sConsumer);
            }
            sEmitter.onNext(joinPoint);
        }
    }
}
