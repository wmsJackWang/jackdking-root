package org.jackdking.login.jwt.utils;

import org.jackdking.login.jwt.domain.UserDetail;

public class SecurityContextHolder {

	private static ThreadLocal<UserDetail> securityUtil = new ThreadLocal<UserDetail>();
	
	public static UserDetail get() {
		
		return securityUtil.get();
	}
	
	public static void set(UserDetail obj) {
		securityUtil.set(obj);
	}
	
}
