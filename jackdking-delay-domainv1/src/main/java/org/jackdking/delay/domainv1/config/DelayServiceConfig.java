package org.jackdking.delay.domainv1.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "delay-service-config")
@Component
public class DelayServiceConfig {
	
	private List<ListenerConfig> listenerConfig;
	
	private List<HandlerConfig> handlerConfig;

	public List<ListenerConfig> getListenerConfig() {
		return listenerConfig;
	}

	public void setListenerConfig(List<ListenerConfig> listenerConfig) {
		this.listenerConfig = listenerConfig;
	}

	public List<HandlerConfig> getHandlerConfig() {
		return handlerConfig;
	}

	public void setHandlerConfig(List<HandlerConfig> handlerConfig) {
		this.handlerConfig = handlerConfig;
	}

	@Override
	public String toString() {
		return "DelayServiceConfig [listenerConfig=" + listenerConfig + ", handlerConfig=" + handlerConfig + "]";
	}
	
}