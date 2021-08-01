package org.jackdking.delay.domainv1.client.handler.Mq;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.jackdking.delay.domainv1.protocol.response.TopicManageResponsePacket;


public class TopicManageResponseHandler  extends SimpleChannelInboundHandler<TopicManageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TopicManageResponsePacket topicManageResponsePacket) throws Exception {

    }
}
