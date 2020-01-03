package org.jackdking.core.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class JdkApplicationContext implements ApplicationContextAware{
	
	public static ApplicationContext jdkApplicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		jdkApplicationContext = applicationContext;
		
	}
}
