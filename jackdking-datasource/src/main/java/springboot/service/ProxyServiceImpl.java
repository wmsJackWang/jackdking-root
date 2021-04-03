package springboot.service;

import springboot.MySolution_dynamicdatasource.proxyautowired.SelfBeanProxyAware;

public class ProxyServiceImpl<T> implements SelfBeanProxyAware<T>{
	
	public T proxyObj;

	@Override
	public void setSelBeanfProxy(T proxyObj) {
		// TODO Auto-generated method stub
		this.proxyObj = proxyObj;
	}
	
	

	

}
