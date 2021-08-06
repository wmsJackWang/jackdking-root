package org.jackdking.delay.domainv1.server.topic.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.jackdking.delay.domainv1.protocol.request.TextMessageRequestPacket;

@Slf4j
public class TextMessageHandler  extends SimpleChannelInboundHandler<TextMessageRequestPacket> {
    public static final TextMessageHandler INSTANCE = new TextMessageHandler();

    private TextMessageHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextMessageRequestPacket textMessageRequestPacket) throws Exception {
        log.info("producer {} send Text message:[{}] To topic [{}]",textMessageRequestPacket.getProducerId(),textMessageRequestPacket.getContent(),textMessageRequestPacket.getProducerId());
    }
}
