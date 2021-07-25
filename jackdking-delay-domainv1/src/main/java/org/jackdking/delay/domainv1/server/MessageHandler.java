package org.jackdking.delay.domainv1.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.jackdking.delay.domainv1.protocol.request.MessageRequestPacket;

@Slf4j
public class MessageHandler  extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket msg) throws Exception {

        log.info("[MessageHandler]接收到客户端信息:{}",msg.toString());
    }
}
