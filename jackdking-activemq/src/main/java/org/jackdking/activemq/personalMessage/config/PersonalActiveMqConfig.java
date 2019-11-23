package org.jackdking.activemq.personalMessage.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@PropertySource("classpath:mq_config.properties")
public class PersonalActiveMqConfig {
	
	
	@Value("${mq.brokerURL}")
    private String mqBrokerURL;
    @Value("${mq.userName}")
    private String mqUserName;
    @Value("${mq.password}")
    private String mqPassword;
    @Value("#{10}")
    private Integer maxConnections;
    @Value("${mq.RedeliveryPolicy.useExponentialBackOff}")
    private boolean useExponentialBackOff;
    @Value("${mq.RedeliveryPolicy.maximumRedeliveries}")
    private Integer maximumRedeliveries;
    @Value("${mq.RedeliveryPolicy.initialRedeliveryDelay}")
    private Long initialRedeliveryDelay; 
    @Value("${mq.RedeliveryPolicy.backOffMultiplier}")
    private Double backOffMultiplier;
    @Value("${mq.RedeliveryPolicy.maximumRedeliveryDelay}")
    private Long maximumRedeliveryDelay;
    @Value("${mq.RedeliveryPolicy.sessionCacheSize}")
    private Integer sessionCacheSize;
    @Value("${mq.personalMessage.queueName}")
    private String  personalMessageQueueName;
    @Value("${mq.personalMessage.topicName}")
    private String personalMessageTopic;
    @Value("${mq.personalMessage.queueTransaction}")
    private String  personalQueueTransaction;

    /*
     * 	消息失效重发策略  定义ReDelivery(重发机制)机制
     */
    @Bean(name = "activeMQRedeliveryPolicy")
    public RedeliveryPolicy activeMQRedeliveryPolicy() {
    	RedeliveryPolicy activeMQRedeliveryPolicy = new RedeliveryPolicy();
    	activeMQRedeliveryPolicy.setUseExponentialBackOff(useExponentialBackOff);
    	activeMQRedeliveryPolicy.setMaximumRedeliveries(maximumRedeliveries);
    	activeMQRedeliveryPolicy.setInitialRedeliveryDelay(initialRedeliveryDelay);
    	activeMQRedeliveryPolicy.setBackOffMultiplier(backOffMultiplier);
    	activeMQRedeliveryPolicy.setMaximumRedeliveryDelay(maximumRedeliveryDelay);
    	return activeMQRedeliveryPolicy;
    }
	
    /**
     * 	真正可以产生Connection的ConnectionFactory，由对应的 JMS服务厂商提供
     *
     * @return 真正的连接工厂
     */
    @Bean(name = "personalTargetConnectionFactory")
    public ActiveMQConnectionFactory activeMQConnectionFactory(@Qualifier("activeMQRedeliveryPolicy")RedeliveryPolicy activeMQRedeliveryPolicy) {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(mqBrokerURL);
        activeMQConnectionFactory.setUserName(mqUserName);
        activeMQConnectionFactory.setPassword(mqPassword);
        activeMQConnectionFactory.setRedeliveryPolicy(activeMQRedeliveryPolicy);
        return activeMQConnectionFactory;
    }
    
    /*
     * PooledConnectionFactory对session和消息producer的缓存机制而带来的性能提升
     */
    @Bean(name = "personalPooledConnectionFactory")
    public PooledConnectionFactory personalPooledConnectionFactory(@Qualifier("personalTargetConnectionFactory") ActiveMQConnectionFactory personalTargetConnectionFactory) {
    	PooledConnectionFactory personalPooledConnectionFactory = new PooledConnectionFactory();
    	personalPooledConnectionFactory.setConnectionFactory(personalTargetConnectionFactory);
    	personalPooledConnectionFactory.setMaxConnections(maxConnections);
    	return personalPooledConnectionFactory;
    }
    
    @Bean(name="personalConnectionFactory")
    public CachingConnectionFactory personalCachingConnectionFactory(@Qualifier("personalPooledConnectionFactory") PooledConnectionFactory personalPooledConnectionFactory) {
    	CachingConnectionFactory personalConnectionFactory = new CachingConnectionFactory();
    	personalConnectionFactory.setTargetConnectionFactory(personalPooledConnectionFactory);
    	
    	personalConnectionFactory.setSessionCacheSize(sessionCacheSize);
    	return personalConnectionFactory;
    }
    
    @Bean("personalQueue")
    public ActiveMQQueue newPersonalQueue() {
    	return new ActiveMQQueue(personalMessageQueueName);
    }
    
    @Bean("personalQueueTransaction")
    public ActiveMQQueue newPersonalTransactionQueue() {
    	return new ActiveMQQueue(personalQueueTransaction);
    }
    
    @Bean("personalTopic")
    public ActiveMQTopic newPersonalMessageTopic() {
    	ActiveMQTopic personalTopic = new ActiveMQTopic(personalMessageTopic);
    	return personalTopic;
    }
    
    @Bean(name="personalJmsTemplate")
    public JmsTemplate newPersonalJmsTemplate(@Qualifier("personalQueue")ActiveMQQueue personalQueue,@Qualifier("personalConnectionFactory")CachingConnectionFactory personalConnectionFactory) {
    	JmsTemplate personalJmsTemplate = new JmsTemplate();
    	personalJmsTemplate.setDefaultDestination(personalQueue);
    	personalJmsTemplate.setConnectionFactory(personalConnectionFactory);
    	return personalJmsTemplate;
    } 
    
    @Bean(name="personalTransactionJmsTemplate")
    public JmsTemplate newPersonalTransactionJmsTemplate(@Qualifier("personalQueueTransaction")ActiveMQQueue personalQueue,@Qualifier("personalConnectionFactory")CachingConnectionFactory personalConnectionFactory) {
    	JmsTemplate personalJmsTemplate = new JmsTemplate();
    	personalJmsTemplate.setDefaultDestinationName(personalQueueTransaction);
    	personalJmsTemplate.setConnectionFactory(personalConnectionFactory);
    	return personalJmsTemplate;
    } 
    
    @Bean(name="personalTopicJmsTemplate")
    public JmsTemplate newPersonalTopicTemplate(@Qualifier("personalConnectionFactory")CachingConnectionFactory personalConnectionFactory,@Qualifier("personalTopic")ActiveMQTopic personalTopic) {
    	JmsTemplate personalTopicJmsTemplate = new JmsTemplate();
    	personalTopicJmsTemplate.setConnectionFactory(personalConnectionFactory);
    	personalTopicJmsTemplate.setDefaultDestination(personalTopic);
    	return personalTopicJmsTemplate;
    }
    
    
}
