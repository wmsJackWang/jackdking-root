package org.jackdking.delay.domainv1.config;

public class HandlerConfig {

	private String handlerName;
	
	private String handlerClassName;

	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	public String getHandlerClassName() {
		return handlerClassName;
	}

	public void setHandlerClassName(String handlerClassName) {
		this.handlerClassName = handlerClassName;
	}

	@Override
	public String toString() {
		return "HandlerConfig [handlerName=" + handlerName + ", handlerClassName=" + handlerClassName + "]";
	}
	
}
