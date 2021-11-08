package org.jackdking.delay.domainv1.service;

public interface TopicManageService {

    public Boolean createTopic(String topic);

    public Boolean subscribeTopic(String topic, String consumerName);


}
