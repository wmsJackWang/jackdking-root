package org.jackdking.common.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface MyMethodAnnotation {
    int layoutId() default 0;
    int viewType() default 0;
    String viewHolder();
}