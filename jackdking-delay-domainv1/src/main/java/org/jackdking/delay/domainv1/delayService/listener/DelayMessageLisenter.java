package org.jackdking.delay.domainv1.delayService.listener;

import org.jackdking.delay.domainv1.delayService.DelayMessageRouterRule;
import org.springframework.data.redis.core.RedisTemplate;

public interface DelayMessageLisenter {
	
	 public void listen();
	 
	 public void setRedisTemplate(RedisTemplate<String, Object>  redisTemplate);
	 
	 public void setDelayMessageRouterRule(DelayMessageRouterRule delayMessageRouterRule);
}