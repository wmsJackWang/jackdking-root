package org.jackdking.delay.domainv1.infrastructure.persistent.cache;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginSessionCache {

    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    private static final Map<String, ChannelGroup> groupIdChannelGroupMap = new ConcurrentHashMap<>();

    public static Map<String, Channel> getUserIdChannelMap() {return userIdChannelMap;}

    public static Map<String, ChannelGroup> getGroupIdChannelGroupMap() {return groupIdChannelGroupMap;}

}
