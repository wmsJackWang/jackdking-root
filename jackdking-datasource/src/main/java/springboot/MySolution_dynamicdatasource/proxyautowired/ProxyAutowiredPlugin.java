package springboot.MySolution_dynamicdatasource.proxyautowired;

import org.springframework.core.Ordered;

public interface ProxyAutowiredPlugin extends Ordered{
	
	void onPrepare();

}
