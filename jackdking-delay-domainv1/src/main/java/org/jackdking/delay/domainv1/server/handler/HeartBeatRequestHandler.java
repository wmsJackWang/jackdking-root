package org.jackdking.delay.domainv1.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.jackdking.delay.domainv1.protocol.request.HeartBeatRequestPacket;
import org.jackdking.delay.domainv1.protocol.response.HeartBeatResponsePacket;

@ChannelHandler.Sharable
@Slf4j
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private HeartBeatRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket requestPacket) {
        log.info("客户端发出的心跳检测请求");
        ctx.writeAndFlush(new HeartBeatResponsePacket());
    }
}
