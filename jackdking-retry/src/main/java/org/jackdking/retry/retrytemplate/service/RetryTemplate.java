package org.jackdking.retry.retrytemplate.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RetryTemplate {
	
	
	private static final Logger log = LoggerFactory.getLogger(RetryTemplate.class);


	private static final int DEFAULT_RETRY_TIME = 3;
    private int retryTime = DEFAULT_RETRY_TIME; 
    private int sleepTime = 3000;// 重试的睡眠时间
    
    public static interface Recover{
    	
    	public String recover();
    }
    
    Recover recover;
    
    
    
    

    public Recover getRecover() {
		return recover;
	}

	public void setRecover(Recover recover) {
		this.recover = recover;
	}

	public int getSleepTime() {
        return sleepTime;
    }

    public RetryTemplate setSleepTime(int sleepTime) {
        if(sleepTime < 0) {
            throw new IllegalArgumentException("sleepTime should equal or bigger than 0");
        }
        this.sleepTime = sleepTime;
        return this;
    }

    public int getRetryTime() {
        return retryTime;
    }

    public RetryTemplate setRetryTime(int retryTime) {
        if (retryTime <= 0) {
            throw new IllegalArgumentException("retryTime should bigger than 0");
        }
        this.retryTime = retryTime;
        return this;
    }

    /**
     * 重试的业务执行代码
     * 失败时请抛出一个异常
     *
     * todo 确定返回的封装类，根据返回结果的状态来判定是否需要重试
     *
     * @return
     */
    protected abstract Object doBiz() throws Exception; //预留一个doBiz方法由业务方来实现，在其中书写需要重试的业务代码，然后执行即可

    public Object execute() throws InterruptedException {
        for (int i = 0; i < retryTime; i++) {
            try {
                return doBiz();
            } catch (Exception e) {
//                log.error("业务执行出现异常，e: {}", e);
                Thread.sleep(sleepTime);
            }
        }
        log.info("业务逻辑失败，重试结束");
        if(recover != null)
        	recover.recover();
        
        return null;
    }

    public Object submit(ExecutorService executorService) {
        if (executorService == null) {
            throw new IllegalArgumentException("please choose executorService!");
        }
        return executorService.submit((Callable) () -> execute());
    }
    
}
