package org.jackdking.delay.domainv1.configuration;

import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.jackdking.delay.domainv1.config.NettyServerConfig;
import org.jackdking.delay.domainv1.server.NettyServer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@Slf4j
public class NettyServerConfiguration {

    @Bean
    public NettyServer nettyServer(NettyServerConfig nettyServerConfig){
        NettyServer nettyServer = null;
        try{
            nettyServer = new NettyServer();
            int nettyServerPort = StringUtils.isEmpty(nettyServerConfig.getPort())?9999:Integer.valueOf(nettyServerConfig.getPort());
            nettyServer.bind(nettyServerPort);
        }catch (Exception e){
            log.error("netty server init fail ， error message:{}",e.getMessage());
            e.printStackTrace();
        }
        return nettyServer;
    }

}
