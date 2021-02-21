package org.jackdking.hystrix.jiangji.conf;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

@Configuration
public class HystrixConfig {

	@Bean
	public ServletRegistrationBean getServlet(){
	    HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
	    ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
	    registrationBean.setLoadOnStartup(1);  //系统启动时加载顺序
	    registrationBean.addUrlMappings("/actuator/hystrix.stream");//路径
	    registrationBean.setName("HystrixMetricsStreamServlet");
	    return registrationBean;
	}
}
