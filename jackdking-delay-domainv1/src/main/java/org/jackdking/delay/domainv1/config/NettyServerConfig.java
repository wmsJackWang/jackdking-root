package org.jackdking.delay.domainv1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "delay-netty-server")
@Component
public class NettyServerConfig {

    private String port;

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }
}
