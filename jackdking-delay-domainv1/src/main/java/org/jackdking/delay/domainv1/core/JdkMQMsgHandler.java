package org.jackdking.delay.domainv1.core;

import io.netty.channel.ChannelHandlerContext;

public interface JdkMQMsgHandler {

    void handleMsg(ChannelHandlerContext ctx, Object msg);
}
