package org.jackdking.delay.domainv1.infrastructure.util;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import org.jackdking.delay.domainv1.infrastructure.attribute.Attributes;
import org.jackdking.delay.domainv1.infrastructure.cache.LoginSessionCache;
import org.jackdking.delay.domainv1.infrastructure.session.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {

    public static void bindSession(Session session, Channel channel) {
        LoginSessionCache.getUserIdChannelMap().put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            Session session = getSession(channel);
            LoginSessionCache.getUserIdChannelMap().remove(session.getUserId());
            channel.attr(Attributes.SESSION).set(null);
            System.out.println(session + " 退出登录!");
        }
    }

    public static boolean hasLogin(Channel channel) {

        return getSession(channel) != null;
    }

    public static Session getSession(Channel channel) {

        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {

        return LoginSessionCache.getUserIdChannelMap().get(userId);
    }

    public static void bindChannelGroup(String groupId, ChannelGroup channelGroup) {
        LoginSessionCache.getGroupIdChannelGroupMap().put(groupId, channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return LoginSessionCache.getGroupIdChannelGroupMap().get(groupId);
    }
}
