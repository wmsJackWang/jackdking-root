package org.jackdking.delay.domainv1.delayService.listener;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.jackdking.delay.domainv1.delayService.DelayMessageRouterRule;
import org.jackdking.delay.domainv1.delayService.DelayServiceWorker;
import org.jackdking.delay.domainv1.delayService.messagetype.DelayMessage;
import org.jackdking.delay.domainv1.delayService.messagetype.NotifyMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
/*
 * 没有失败情况处理
 */
@Slf4j
public class JdkDelayMessageListener implements DelayMessageLisenter{
	
	RedisTemplate< String, Object>  redisTemplate;
	
	DelayMessageRouterRule  delayMessageRouterRule;
	
	public void setRedisTemplate(RedisTemplate< String, Object>  redisTemplate) {
		this.redisTemplate = redisTemplate;
	};

	@Override
	public void listen() {
		// TODO Auto-generated method stub

	    log.info("消费消息开始");
		long now = 0;
		do {
		    now = System.currentTimeMillis();
		    //拿出延迟队里中超过当前时间的消息
		    Set<TypedTuple<Object>> tupleSet = redisTemplate.opsForZSet().rangeByScoreWithScores(DelayServiceWorker.DELAY_QUEUE, 0, now);
		    
		    if(tupleSet.size()>0)
		    {
			    for (TypedTuple<Object> t : tupleSet) {
			        String message = String.valueOf(t.getValue());
			        DelayMessage<String> msg = new NotifyMessage.Builder<String>(0L,message,message).build();
			        msg.setPlayload(message);
			        msg.setUniqueKey(message);
			        delayMessageRouterRule.route(msg);
			        if(StringUtils.isEmpty(message)) continue;

			    }
			    //执行完过期退款操作后，就将过期订单删除掉
			    redisTemplate.opsForZSet().removeRangeByScore(DelayServiceWorker.DELAY_QUEUE, 0, now);
		    }
			else {
				try {
					TimeUnit.SECONDS.sleep(5);//红包退款不是实时性的操作，因此这里延迟队列没有过期红包订单就休眠10分钟，然后重新检测。
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}while(true);
		
	}

	@Override
	public void setDelayMessageRouterRule(DelayMessageRouterRule delayMessageRouterRule) {
		// TODO Auto-generated method stub
		this.delayMessageRouterRule = delayMessageRouterRule;
	}
}
