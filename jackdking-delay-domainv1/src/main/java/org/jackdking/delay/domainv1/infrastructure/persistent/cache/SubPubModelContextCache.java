package org.jackdking.delay.domainv1.infrastructure.persistent.cache;

import com.google.common.collect.Sets;
import io.netty.channel.Channel;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/*
 *  连接后，发出消费者身份命令后，直接将连接id 放到consumerToInstanceIdOlineList容器中。
 *  断开后，如果身份是 消费者实例， 则直接从容器consumerToInstanceIdOlineList中删除该实例
 */
public class SubPubModelContextCache {

    //topic集合
    public Set<String> topicList = Sets.newConcurrentHashSet();

    //topic对应的队列集合
    public ConcurrentHashMap<String, List<String>> topicToQueueList = new ConcurrentHashMap<>();

    //topic的消费者名称集合
    public ConcurrentHashMap<String, List<String>> topicConsumerList = new ConcurrentHashMap<>();

    //消费者名称 对应的在线消费者实例，实例在线就插入到消费者名称对应的list中去
    public ConcurrentHashMap<String, List<String>> consumerToInstanceIdOlineList = new ConcurrentHashMap<>();

    //在线实例对应的channel对象，连接时候放入到容器，断开连接后从容器中删除
    public ConcurrentHashMap<String, Channel> onLineConsumerIdToChannel = new ConcurrentHashMap<>();


}
