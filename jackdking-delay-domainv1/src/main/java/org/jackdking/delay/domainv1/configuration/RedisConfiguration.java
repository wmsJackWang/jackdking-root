package org.jackdking.delay.domainv1.configuration;

import org.jackdking.delay.domainv1.config.DelayServiceConfig;
import org.jackdking.delay.domainv1.delayService.DelayMessageRouterRule;
import org.jackdking.delay.domainv1.delayService.DelayServiceWorker;
import org.jackdking.delay.domainv1.delayService.excutor.DelayQueueExcuetor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisConfiguration {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean("redisTemplate")
	public RedisTemplate redisTemplate(@Lazy RedisConnectionFactory connectionFactory) {
		RedisTemplate redis = new RedisTemplate();
		GenericToStringSerializer<String> keySerializer = new GenericToStringSerializer<String>(String.class);
		//key序列化方式
		redis.setKeySerializer(keySerializer);
		//哈希key序列化方式
		redis.setHashKeySerializer(keySerializer);
 
		GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();
		//值序列化方式
		redis.setValueSerializer(valueSerializer);
		//哈希值序列化方式
		redis.setHashValueSerializer(valueSerializer);
		//连接器工厂
		redis.setConnectionFactory(connectionFactory);
 
		return redis;
	}
	
	
	@Bean
	public DelayServiceWorker newDelayService(RedisTemplate<String, Object> redisTemplate, DelayMessageRouterRule delayMessageRouterRule, DelayServiceConfig delayServiceConfig) {
		
		DelayServiceWorker delayService = new DelayServiceWorker();
		
		System.out.println(delayServiceConfig.toString());
		
		delayService.setRedisTemplate(redisTemplate)
					.setConfig(delayServiceConfig)
					.setDelayMessageRouterRule(delayMessageRouterRule)
					.init()
					.start();
		
		return delayService;
		
	}

	@Bean
	public DelayMessageRouterRule newDelayMessageRouterRule(DelayQueueExcuetor excuetor) {
		
		DelayMessageRouterRule delayMessageRouterRule = new DelayMessageRouterRule();
		
		delayMessageRouterRule.setExcuetor(excuetor)
							  .init();
		
		return delayMessageRouterRule;
	}

}

