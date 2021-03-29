package org.jackdking.activemq.normalMessage.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.jackdking.activemq.normalMessage.consumerlistener.NormalMessageListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;


@Configuration
@PropertySource("classpath:mq_config.properties")
public class ActiveMqListenerConfig {

    @Value("${orderQueryQueueName.query}")
    private String orderQueryQueueDestinationName;
    
    @Value("${tradeQueueName.notify}")
    private String tradeQueueNotifyDestination;

    /**
     * 	队列目的地
     *
     * @return 队列目的地
     */
    @Bean(name = "orderQueryQueueDestination")
    public ActiveMQQueue orderQueryQueueDestination() {
        return new ActiveMQQueue(orderQueryQueueDestinationName);
    }
    
    @Bean(name = "tradeQueueNotifyDestination")
    public ActiveMQQueue tradeQueueNotifyDestination() {
        return new ActiveMQQueue(tradeQueueNotifyDestination);
    }
    
    @Bean//设置容器重连默认5次
    public FixedBackOff backOff() {
    	FixedBackOff backOff = new FixedBackOff();
    	backOff.setMaxAttempts(5);
		return backOff;
    }

    /**
     * 	消息监听容器
     *
     * @param singleConnectionFactory    连接工厂
     * @param orderQueryQueueDestination 消息目的地
     * @param pollingMessageListener     监听器实现
     * @return 消息监听容器 	这个是关联消费者的，监听器就是消费者。
     */
    @Bean(name = "tradeQueueNotifyMessageListenerContainer")
    public DefaultMessageListenerContainer orderQueryQueueMessageListenerContainer(@Qualifier("connectionFactory") SingleConnectionFactory singleConnectionFactory
    									, @Qualifier("tradeQueueNotifyDestination") ActiveMQQueue tradeQueueNotifyDestination
    									, @Qualifier("NormalMessageListener") NormalMessageListener normalMessageListener
    									, FixedBackOff backOff) {
        DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(singleConnectionFactory);
        messageListenerContainer.setDestination(tradeQueueNotifyDestination);
        messageListenerContainer.setMessageListener(normalMessageListener);
        messageListenerContainer.setBackOff(backOff);
        return messageListenerContainer;//sessionAcknowledgeMode = Session.AUTO_ACKNOWLEDGE;消息确认模式默认为自动确认
    }

    /*
     * 	这个是关联消费者的，监听器就是消费者。
     */
    @Bean(name = "orderQueryQueueMessageListenerContainer")
    public DefaultMessageListenerContainer tradeQueueNotifyDestinationListenerContainer(@Qualifier("connectionFactory") SingleConnectionFactory singleConnectionFactory, 
    																					@Qualifier("orderQueryQueueDestination") ActiveMQQueue orderQueryQueueDestination, 
    																					@Qualifier("NormalMessageListener") NormalMessageListener normalMessageListener,
    																					FixedBackOff backOff) {
        DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(singleConnectionFactory);
        messageListenerContainer.setDestination(orderQueryQueueDestination);
        messageListenerContainer.setMessageListener(normalMessageListener);
        messageListenerContainer.setBackOff(backOff);
        return messageListenerContainer;//sessionAcknowledgeMode = Session.AUTO_ACKNOWLEDGE;消息确认模式默认为自动确认
    }

}
