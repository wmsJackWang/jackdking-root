package org.jackdking.core.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class JdkApplicationContext implements ApplicationContextAware{
	
	private static final Logger log = LoggerFactory.getLogger(JdkApplicationContext.class);

	
	public static ApplicationContext jdkApplicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		log.info("对jdkApplicationContext进行赋值");
		
		jdkApplicationContext = applicationContext;
		
	}
}
