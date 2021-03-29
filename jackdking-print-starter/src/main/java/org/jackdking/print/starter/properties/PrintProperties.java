package org.jackdking.print.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "print")
public class PrintProperties {

	private String serviceName = "hello world";

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String getServiceName() {
		return serviceName;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[serviceName="+this.serviceName+"]";
	}
}
