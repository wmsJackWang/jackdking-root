package org.jackdking.delay.domainv1.server.topic.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.jackdking.core.common.JdkApplicationContext;
import org.jackdking.delay.domainv1.delayService.DelayServiceWorker;
import org.jackdking.delay.domainv1.delayService.messagetype.NotifyMessage;
import org.jackdking.delay.domainv1.infrastructure.translator.DelayMessageTranslator;
import org.jackdking.delay.domainv1.protocol.request.TextMessageRequestPacket;

@Slf4j
public class TextMessageHandler  extends SimpleChannelInboundHandler<TextMessageRequestPacket> {
    public static final TextMessageHandler INSTANCE = new TextMessageHandler();

    private JdkApplicationContext jdkApplicationContext;

    private TextMessageHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextMessageRequestPacket textMessageRequestPacket) throws Exception {
        log.info("producer {} send Text message:[{}] To topic [{}]",textMessageRequestPacket.getProducerId(),textMessageRequestPacket.getContent(),textMessageRequestPacket.getProducerId());

    }
}
