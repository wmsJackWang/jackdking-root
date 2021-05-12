package org.jackdking.delay.domainv1.delayService;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.util.StringUtils;

public class JdkDelayMessageListener implements DelayMessageLisenter{
	
	RedisTemplate< String, Object>  redisTemplate;
	
	public void setRedisTemplate(RedisTemplate< String, Object>  redisTemplate) {
		this.redisTemplate = redisTemplate;
	};

	@Override
	public void listen() {
		// TODO Auto-generated method stub

		long now = 0;
		do {
		    System.out.println("消费消息开始");
		    now = System.currentTimeMillis();
		    //拿出延迟队里中超过当前时间的消息
		    Set<TypedTuple<Object>> tupleSet = redisTemplate.opsForZSet().rangeByScoreWithScores(DelayService.DELAY_QUEUE, 0, now);
		    
		    if(tupleSet.size()>0)
		    {
			    for (TypedTuple t : tupleSet) {
			        String message = String.valueOf(t.getValue());
			        System.out.println("消费消息："+message);
			        if(StringUtils.isEmpty(message)) continue;
			    	
			    }
			    //执行完过期退款操作后，就将过期订单删除掉
			    redisTemplate.opsForZSet().removeRangeByScore(DelayService.DELAY_QUEUE, 0, now);
		    }
			else
				try {
					TimeUnit.SECONDS.sleep(5);//红包退款不是实时性的操作，因此这里延迟队列没有过期红包订单就休眠3分钟，然后重新检测。
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    
		   
		    System.out.println("消费消息 over");
			
			
		}while(true);
		
	}
	
	
	

}
