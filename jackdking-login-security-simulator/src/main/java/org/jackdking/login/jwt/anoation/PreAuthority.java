package org.jackdking.login.jwt.anoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PreAuthority {
	
	String[] roles() default {}; // 权限默认  为空，为空的话 就不做权限拦截

}
