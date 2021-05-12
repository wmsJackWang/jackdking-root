package org.jackdking.delay.domainv1;

import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.jackdking.delay.domainv1.delayService.DelayMessage;
import org.jackdking.delay.domainv1.delayService.DelayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.util.StringUtils;


/**
 * Hello world!
 *
 */
@SpringBootApplication
public class Application implements CommandLineRunner
{
	Logger logger = Logger.getLogger(DelayService.class.getName());
	
	@Autowired
	DelayService delayService;
	
	@Autowired
	RedisTemplate<String, Object> redisTemplate ;
	
	
    public static void main( String[] args )
    {
        SpringApplication.run(Application.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> msg = new HashMap<String, String>();
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
		
		while (true) {
			
			DelayMessage<Map> message = new DelayMessage<Map>();
			message.setExpireTime(System.currentTimeMillis()+10);//测试10s后过期
			message.setUniqueKey(""+id++);
			message.setPlayload(msg);
			delayService.sendDelayMsg(message);
			logger.info("发送消息"+message.getMessageJson());
			TimeUnit.SECONDS.sleep(1);
		}
	}

	protected void work() {
		// TODO Auto-generated method stub
		
		long now = 0;
		String eles[];
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
