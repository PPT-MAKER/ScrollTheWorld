package com.bignerdranch.android.scrolltheworld.common.Utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by huangwt on 2018/10/10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SingleClick {
    long timeValue() default 1000L;

    int filterType() default 1;

    BanClickUtil.Order order() default BanClickUtil.Order.FIRST;
}
