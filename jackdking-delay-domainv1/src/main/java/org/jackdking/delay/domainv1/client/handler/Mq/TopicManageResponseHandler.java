package org.jackdking.delay.domainv1.client.handler.Mq;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.jackdking.delay.domainv1.protocol.response.TopicManageResponsePacket;

@Slf4j
public class TopicManageResponseHandler  extends SimpleChannelInboundHandler<TopicManageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TopicManageResponsePacket topicManageResponsePacket) throws Exception {
            log.info("send topic[{}] success : {}",topicManageResponsePacket.getTopic(),topicManageResponsePacket.isSuccess());
    }
}
