package com.jackdking.rw.separation.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InterceptAnnotation {

    String value() default "";

    /**
     * true增强、false忽略
     */
    boolean flag() default true;
}
