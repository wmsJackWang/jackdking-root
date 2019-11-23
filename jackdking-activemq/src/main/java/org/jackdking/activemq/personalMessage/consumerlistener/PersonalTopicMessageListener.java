package org.jackdking.activemq.personalMessage.consumerlistener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;

@Component("personalTopicMessageListener")
public class PersonalTopicMessageListener implements MessageListener {

    public void onMessage(Message message) {

        TextMessage textMessage = (TextMessage) message;

        try {
            System.out.println("我是topic监听器，接收到消息:" + textMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}