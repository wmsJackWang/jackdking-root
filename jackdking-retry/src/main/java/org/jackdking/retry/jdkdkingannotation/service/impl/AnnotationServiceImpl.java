package org.jackdking.retry.jdkdkingannotation.service.impl;

import java.time.LocalTime;

import org.jackdking.retry.jdkdkingannotation.annotation.JdkRetry;
import org.jackdking.retry.jdkdkingannotation.retryException.UpdateRetryException;
import org.jackdking.retry.jdkdkingannotation.service.AnnotationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("AnnotationServiceImpl")
@Transactional
public class AnnotationServiceImpl implements AnnotationService{

	@Transactional
	@JdkRetry(exception=UpdateRetryException.class)
	public int testService() throws Exception {
		// TODO Auto-generated method stub
		this.coreService();
		return 200;
	}
	
	public void coreService() throws Exception{
		
		int code = 0;
		System.out.println("AnnotationServiceImpl被调用,时间："+LocalTime.now());
		if (code==0){
		    throw new UpdateRetryException("业务执行失败情况！");
		}
		System.out.println("AnnotationServiceImpl执行成功！");

	}

}
