package org.jackdking.activemq.personalMessage.consumerlistener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

@Component("personalQueueMessageListenerTransaction")
public class PersonalQueueMessageListenerTransaction  implements SessionAwareMessageListener<Message> {//消息确认需要session，需要实现SessionAwareMessageListener
    int num = 0;
    @Override
    public void onMessage(Message message, Session session)  throws JMSException {
        if (message instanceof TextMessage) {
            String msg = ((TextMessage) message).getText();

            System.out.println("------------------------------------------");
            System.out.println("事务管理消费者收到的消息：" + msg);
            System.out.println("------------------------------------------");

            try {
                if ("test2".equals(msg)) {
                    throw new RuntimeException("故意抛出的异常");
                }
                // 确认消息。只要被确认后  就会出队，接受失败没有确认成功，会在原队列里面
                //message.acknowledge();
                num++;
                if(num == 3) {
                    session.commit();
                }

            } catch (Exception e) {

               session.rollback();

            }
        }
    }
}