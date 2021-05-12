package org.jackdking.delay.domainv1.delayService;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jackdking.delay.domainv1.config.ExecutorThreadsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DelayQueueExcuetor {
	

	private static Logger logger = LogManager.getLogger(DelayQueueExcuetor.class);// 日志服务

	private ThreadPoolExecutor threadPoolExecutor ;
	

	@Autowired
	public DelayQueueExcuetor(ExecutorThreadsConfig  executorThreadsConfig){
		
		logger.info("executorThreadsConfig配置信息:{}",executorThreadsConfig.toString());

		threadPoolExecutor = new ThreadPoolExecutor(Integer.parseInt(executorThreadsConfig.getCorepoolsize()), Integer.parseInt(executorThreadsConfig.getMaxpoolsize()), Long.parseLong(executorThreadsConfig.getKeepalivetime()), TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>( Integer.parseInt(executorThreadsConfig.getQueuecapacity())),
				new ThreadPoolExecutor.CallerRunsPolicy());
		
		
		threadPoolExecutor.allowCoreThreadTimeOut(Boolean.parseBoolean(executorThreadsConfig.getAllow()));
		 logger.info("线程池【AsynchronousBathSubmitDataBase】启动参数  corepoolsize:" +executorThreadsConfig.getCorepoolsize()+"|maxpoolsize:"+executorThreadsConfig.getMaxpoolsize()+"|keepalivetime:"+executorThreadsConfig.getKeepalivetime()+"|allow:"+executorThreadsConfig.getAllow()+"|queuecapacity:"+executorThreadsConfig.getQueuecapacity());
	}
	
	@SuppressWarnings("rawtypes")
	public Future submit(Runnable job){
		return threadPoolExecutor.submit(job);
	}
	
}
