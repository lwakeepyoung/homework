package com.lwa.week.annotation;

import java.lang.annotation.*;

/**
 * @Author: lwa
 * @Date: 2021/11/7 16:02
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurDataSource {
    String name() default "";
}
