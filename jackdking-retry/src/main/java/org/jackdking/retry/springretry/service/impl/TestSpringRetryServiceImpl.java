package org.jackdking.retry.springretry.service.impl;

import java.time.LocalTime;

import org.jackdking.retry.springretry.service.TestSpringRetryService;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class TestSpringRetryServiceImpl implements TestSpringRetryService {

	@Override
	@Retryable(value = Exception.class,maxAttempts = 3  ,backoff = @Backoff(delay = 2000,multiplier = 1.5))
	public int retryServiceOne(int code) throws Exception {
		// TODO Auto-generated method stub 
		System.out.println("retryServiceOne被调用,时间："+LocalTime.now());
		System.out.println("执行当前业务逻辑的线程名："+Thread.currentThread().getName());
		if (code==0){
		    throw new Exception("业务执行失败情况！");
		}
		System.out.println("retryServiceOne执行成功！");

		return 200;
	}

	@Recover
	public int recover(Exception e) {
		System.out.println("回调方法执行！！！！");
        //业务方法执行失败处理逻辑：记日志到数据库 或者调用其余的方法
        return 400;
	}
}
