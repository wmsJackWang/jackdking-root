package org.jackdking.delay.domainv1.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.jackdking.delay.domainv1.delayService.DelayServiceWorker;
import org.jackdking.delay.domainv1.delayService.messagetype.NotifyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TestComponent  implements CommandLineRunner{

	Logger logger = Logger.getLogger(DelayServiceWorker.class.getName());
	
	@Autowired
	DelayServiceWorker delayService;
	
	@Autowired
	RedisTemplate<String, Object> redisTemplate ;
	

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> msg = new HashMap<String, Object>();
		msg.put("data", "data");
		int id = 0;
		
//		new Thread((new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				
//				work();
//				
//			}
//		})).start();
//		
//		while (true) {
//			NotifyMessage<Map<String,Object>> message = new NotifyMessage.Builder<Map<String,Object>>(System.currentTimeMillis()+10, ""+id++, "notifyMsg")
//					    .playload(msg)
//					    .build();
//			delayService.sendDelayMsg(message);
//			logger.info("发送消息"+message.getMessageJson());
//			TimeUnit.SECONDS.sleep(1);
//		}
	}

	protected void work() {
		// TODO Auto-generated method stub
		
		long now = 0;
		do {
		    System.out.println("消费消息开始");
		    now = System.currentTimeMillis();
		    //拿出延迟队里中超过当前时间的消息
		    Set<TypedTuple<Object>> tupleSet = redisTemplate.opsForZSet().rangeByScoreWithScores(DelayServiceWorker.DELAY_QUEUE, 0, now);
		    
		    if(tupleSet.size()>0)
		    {
			    for (TypedTuple<Object> tuple : tupleSet) {
			        String message = String.valueOf(tuple.getValue());
			        System.out.println("消费消息："+message);
			        if(StringUtils.isEmpty(message)) continue;
			    	
			    }
			    //执行完过期退款操作后，就将过期订单删除掉
			    redisTemplate.opsForZSet().removeRangeByScore(DelayServiceWorker.DELAY_QUEUE, 0, now);
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
