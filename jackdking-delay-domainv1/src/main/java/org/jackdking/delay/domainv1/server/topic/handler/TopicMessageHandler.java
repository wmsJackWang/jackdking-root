package org.jackdking.delay.domainv1.server.topic.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.jackdking.delay.domainv1.protocol.Packet;
import org.jackdking.delay.domainv1.server.handler.*;

import java.util.HashMap;
import java.util.Map;

import static org.jackdking.delay.domainv1.protocol.command.Command.*;

@ChannelHandler.Sharable
public class TopicMessageHandler  extends SimpleChannelInboundHandler<Packet> {
    public static final TopicMessageHandler INSTANCE = new TopicMessageHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private TopicMessageHandler() {
        handlerMap = new HashMap<>();
        handlerMap.put(TEXT_MESSAGE_REQUEST, TextMessageHandler.INSTANCE);

        handlerMap.put(MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
        handlerMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE);
        handlerMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestHandler.INSTANCE);
        handlerMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE);
        handlerMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE);
        handlerMap.put(GROUP_MESSAGE_REQUEST, GroupMessageRequestHandler.INSTANCE);
        handlerMap.put(LOGOUT_REQUEST, LogoutRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        handlerMap.get(packet.getCommand()).channelRead(ctx, packet);
    }
}
