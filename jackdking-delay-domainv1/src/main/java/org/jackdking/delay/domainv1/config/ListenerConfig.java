package org.jackdking.delay.domainv1.config;

import java.util.List;

public class ListenerConfig {
	
	private String listenerName;
	
	private String listenerClassName;
	
	private List<String> handlerName;

	public String getListenerName() {
		return listenerName;
	}

	public void setListenerName(String listenerName) {
		this.listenerName = listenerName;
	}

	public String getListenerClassName() {
		return listenerClassName;
	}

	public void setListenerClassName(String listenerClassName) {
		this.listenerClassName = listenerClassName;
	}

	public List<String> getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(List<String> handlerName) {
		this.handlerName = handlerName;
	}

	@Override
	public String toString() {
		return "ListenerConfig [ListenerName=" + listenerName + ", ListenerClassName=" + listenerClassName
				+ ", handlerName=" + handlerName + "]";
	}
	
}
