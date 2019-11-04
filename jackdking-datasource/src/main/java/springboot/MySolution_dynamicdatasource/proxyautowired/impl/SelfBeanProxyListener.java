package springboot.MySolution_dynamicdatasource.proxyautowired.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import springboot.MySolution_dynamicdatasource.proxyautowired.ProxyAutowiredPlugin;

@Component
public class SelfBeanProxyListener implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private List<ProxyAutowiredPlugin> selfBeanProxyManagers;
//    @Autowired
//    public void setSystemBootAddons(List<SelfBeanProxyManager> selfBeanProxyManagers) {
//        this.selfBeanProxyManagers = selfBeanProxyManagers;
//    }
	
    private boolean hasRunOnce = false;
    
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		if (!hasRunOnce) {
            for (ProxyAutowiredPlugin selfBeanProxyManager :selfBeanProxyManagers) {
            	selfBeanProxyManager.onPrepare();
            }
            hasRunOnce = true;
        }
	}
}
