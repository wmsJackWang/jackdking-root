package org.jackdking.delay.domainv1.delayService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.jackdking.delay.domainv1.config.DelayServiceConfig;
import org.jackdking.delay.domainv1.config.ListenerConfig;
import org.jackdking.delay.domainv1.controller.MainController;
import org.jackdking.delay.domainv1.delayService.listener.DelayMessageLisenter;
import org.jackdking.delay.domainv1.delayService.messagetype.NotifyMessage;
import org.jackdking.delay.domainv1.exceptions.BussinessErrorException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author mingshengwang
 *
 */
//@Service
@Slf4j
public class DelayServiceWorker {
	
	public static final String DELAY_QUEUE = "DELAY_QUEUE";
	
	private List<DelayMessageLisenter> listeners = new ArrayList();
	
	private ExecutorService executorService = null;
	
	private String listenerClassName = "org.jackdking.delay.domainv1.delayService.JdkDelayMessageListener";
	
	private DelayServiceConfig config;
	
//	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	
	DelayMessageRouterRule delayMessageRouterRule;
	
	/*
	 * 发送延迟消息  ，如果消息发送失败，方法抛出异常，供业务回滚事务
	 * 
	 * 描述： 该方法是同步发送消息到redis中，redis客户端识别成功了，才算业务发送消息成功。
	 * 
	 * 问题（消息发送的可靠性）： 可能存在，消息已经发送到redis中了，但是本地因为网络原因失败了，所以业务失败了，但是消息发送redis成功了。【基于本地消息实现 最终可靠性】
	 * 
	 * 问题： 如果所有数据都放在一个zset数据结构中，如果有大批量数据失效，那就回存在大key问题。
	 * 
	 */
	public void sendDelayMsg(NotifyMessage msg) throws Exception{
		
		if(StringUtils.isEmpty(msg.getUniqueKey())) {
			log.info("【延迟消息服务】消息发送失败:消息唯一key为空。message:{}",msg.getMessageJson());
			throw new BussinessErrorException("【延迟消息服务】消息发送失败:消息唯一key为空"+msg.getMessageJson());
		}
		
		boolean result = false;
		try {
			result = redisTemplate.opsForZSet().add(DELAY_QUEUE, msg.getUniqueKey(), msg.getExpireTime());
		}catch (Exception e) {
			// TODO: handle exception
			throw new BussinessErrorException("【延迟消息服务】消息发送失败:发送到redis服务器异常"+msg.getMessageJson());
		}
		
		if(!result)
			throw new BussinessErrorException("【延迟消息服务】消息发送失败:发送到redis服务器失败"+msg.getMessageJson());
		
	}
	
	public DelayServiceWorker setDelayMessageRouterRule(DelayMessageRouterRule delayMessageRouterRule) {
		this.delayMessageRouterRule = delayMessageRouterRule;
		return this;
	}
	
	public DelayServiceWorker setConfig(DelayServiceConfig config) {
		this.config = config;
		return this;
	}
	
	public DelayServiceWorker setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
		return this;
	}
	
	public DelayServiceWorker init() {
		
		try {
			if(config.getListenerConfig()==null)
				throw new BussinessErrorException("延迟服务启动失败，没有设置延迟监听器！");
			for(ListenerConfig listenerConfig : config.getListenerConfig()) {
				
				Class<DelayMessageLisenter> listenerClass  = (Class<DelayMessageLisenter>) Class.forName(listenerConfig.getListenerClassName());
				DelayMessageLisenter listener = listenerClass.newInstance(); 
				listener.setRedisTemplate(redisTemplate);
				listener.setDelayMessageRouterRule(delayMessageRouterRule);
				listeners.add(listener);
				
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this;
	}
	
	//开启延迟服务，入口
	public void start() {
		executorService = Executors.newFixedThreadPool(listeners.size());
		if(listeners.size()>0)
			for(DelayMessageLisenter listener:listeners) {
				executorService.submit(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						listener.listen();
					}
				});
			}
			
		
	}
	
	

}
