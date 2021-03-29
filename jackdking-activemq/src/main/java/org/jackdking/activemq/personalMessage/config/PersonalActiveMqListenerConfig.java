package org.jackdking.activemq.personalMessage.config;

import org.jackdking.activemq.personalMessage.consumerlistener.PersonalQueueMessageListener;
import org.jackdking.activemq.personalMessage.consumerlistener.PersonalQueueMessageListenerTransaction;
import org.jackdking.activemq.personalMessage.consumerlistener.PersonalTopicMessageListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@PropertySource("classpath:mq_config.properties")
public class PersonalActiveMqListenerConfig {

    @Value("${mq.personalMessage.queueName}")
    private String  personalMessageQueueName;
    @Value("${mq.personalMessage.topicName}")
    private String personalMessageTopic;    
    @Value("${mq.personalMessage.queueTransaction}")
    private String  personalQueueTransaction;
    
    /**
     * 	消息监听容器
     *
     * @param singleConnectionFactory    连接工厂
     * @param orderQueryQueueDestination 消息目的地
     * @param pollingMessageListener     监听器实现
     * @return 消息监听容器 	这个是关联消费者的，监听器就是消费者。
     */
    @Bean(name = "personalQueueMessageListenerContainer")
    public DefaultMessageListenerContainer orderQueryQueueMessageListenerContainer(@Qualifier("personalConnectionFactory") CachingConnectionFactory personalConnectionFactory, 
    																			   @Qualifier("personalQueueMessageListener") PersonalQueueMessageListener personalQueueMessageListener,
    																			   FixedBackOff backOff) {
        DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(personalConnectionFactory);
        messageListenerContainer.setDestinationName(personalMessageQueueName);
        messageListenerContainer.setMessageListener(personalQueueMessageListener);
        messageListenerContainer.setSessionAcknowledgeMode(4);
        messageListenerContainer.setBackOff(backOff);
        return messageListenerContainer;//sessionAcknowledgeMode = Session.AUTO_ACKNOWLEDGE;消息确认模式默认为自动确认
    }

    /*
     * 	这个是关联消费者的，监听器就是消费者。
     */
    @Bean(name = "personalTopicMessageListenerContainer")
    public DefaultMessageListenerContainer tradeQueueNotifyDestinationListenerContainer(@Qualifier("personalConnectionFactory") CachingConnectionFactory personalConnectionFactory,  
    																					@Qualifier("personalTopicMessageListener") PersonalTopicMessageListener personalTopicMessageListener,
    																					FixedBackOff backOff) {
        DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(personalConnectionFactory);
        messageListenerContainer.setDestinationName(personalMessageTopic);
        messageListenerContainer.setMessageListener(personalTopicMessageListener);
        messageListenerContainer.setBackOff(backOff);
        return messageListenerContainer;//sessionAcknowledgeMode = Session.AUTO_ACKNOWLEDGE;消息确认模式默认为自动确认
    }
    
    
    /*
     * 	这个是关联消费者的，监听器就是消费者。
     * 	AUTO_ACKNOWLEDGE = 1    自动确认
        CLIENT_ACKNOWLEDGE = 2    客户端手动确认
        DUPS_OK_ACKNOWLEDGE = 3    自动批量确认
        SESSION_TRANSACTED = 0    事务提交并确认
        INDIVIDUAL_ACKNOWLEDGE = 4    单条消息确认
     */
    @Bean(name = "jmsContainerTransaction")
    public DefaultMessageListenerContainer jmsContainerTransaction(@Qualifier("personalConnectionFactory") CachingConnectionFactory personalConnectionFactory,  
    															   @Qualifier("personalQueueMessageListenerTransaction") PersonalQueueMessageListenerTransaction personalQueueMessageListenerTransaction,
    															   @Qualifier("jmsTransactionManager")JmsTransactionManager jmsTransactionManager,
    															   FixedBackOff backoff) {
        DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(personalConnectionFactory);
        messageListenerContainer.setDestinationName(personalQueueTransaction);
        messageListenerContainer.setMessageListener(personalQueueMessageListenerTransaction);
        messageListenerContainer.setSessionAcknowledgeMode(4);
        messageListenerContainer.setBackOff(backoff);
        //对消息开启事务模式
        messageListenerContainer.setSessionTransacted(true);
        messageListenerContainer.setTransactionManager(jmsTransactionManager);
        
        return messageListenerContainer;//sessionAcknowledgeMode = Session.AUTO_ACKNOWLEDGE;消息确认模式默认为自动确认
    }
    
    //添加事务管理器，和数据库事务管理器一起使用，使得和数据库在同一个事务中
    @Bean("jmsTransactionManager")
    public JmsTransactionManager jmsTransactionManager(@Qualifier("personalConnectionFactory") CachingConnectionFactory personalConnectionFactory) {
    	JmsTransactionManager jmsTransactionManager = new JmsTransactionManager(personalConnectionFactory);
    	return jmsTransactionManager;
    }
    
    
}
