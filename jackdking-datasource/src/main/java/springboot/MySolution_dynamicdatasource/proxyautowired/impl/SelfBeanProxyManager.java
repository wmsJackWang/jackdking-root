package springboot.MySolution_dynamicdatasource.proxyautowired.impl;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import springboot.MySolution_dynamicdatasource.proxyautowired.ProxyAutowiredPlugin;
import springboot.MySolution_dynamicdatasource.proxyautowired.SelfBeanProxyAware;

@Component
public class SelfBeanProxyManager implements ApplicationContextAware, ProxyAutowiredPlugin{
	
	 private ApplicationContext applicationContext;

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return Ordered.HIGHEST_PRECEDENCE;
	}

	@Override
	public void onPrepare() {
		// TODO Auto-generated method stub
		// 从 bean容器中 获取对象，如果对象存在代理对象，则容器返回的是代理对象
		Map<String, SelfBeanProxyAware> proxyAwares = applicationContext.getBeansOfType(SelfBeanProxyAware.class);
        for (SelfBeanProxyAware proxyAware : proxyAwares.values()) {
            proxyAware.setSelBeanfProxy(proxyAware);
        }
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}

}
