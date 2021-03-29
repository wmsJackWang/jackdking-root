package org.jackdking.aop.log.anoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OperationLog {

    /**	
     * 操作描述
     *
     * @return 操作内容描述
     */
    String expression() ;


    /**
     * 操作
     *
     * @return 操作
     */
    String option();


    /**
     * 此值可用于解析SpEl表达式获取方法.
     *
     * @return method返回值
     */
    String value() default "";

}