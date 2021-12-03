package org.jackdking.delay.domainv1.core;

import io.netty.channel.ChannelHandlerContext;

public class JdkMQMsgConsumerHandler extends JdkMQMsgWrapHandler{



    @Override
    public void handleMsg(ChannelHandlerContext ctx, Object msg) {
        super.handleMsg(ctx, msg);
    }
}
