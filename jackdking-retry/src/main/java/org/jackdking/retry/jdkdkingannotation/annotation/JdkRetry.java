package org.jackdking.retry.jdkdkingannotation.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 重试注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JdkRetry{
	
	//默认
	 int maxAttempts() default 3;
	//默认每次间隔等待3000毫秒
	 long waitTime() default 3000;
	
	//捕捉到的异常类型  再进行重发
	 Class<?> exception () default Exception.class ;
	
	 String recoverServiceName () default "DefaultRecoverImpl";
	
}