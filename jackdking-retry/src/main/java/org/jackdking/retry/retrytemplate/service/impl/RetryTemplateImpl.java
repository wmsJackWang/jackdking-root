package org.jackdking.retry.retrytemplate.service.impl;

import java.time.LocalTime;

import org.jackdking.retry.retrytemplate.service.RetryTemplate;
import org.springframework.stereotype.Service;

@Service("serviceone")
public class RetryTemplateImpl extends RetryTemplate{
	
	public RetryTemplateImpl() {
		// TODO Auto-generated constructor stub
		this.setRecover(new RecoverImpl());
	}

	@Override
	protected Object doBiz() throws Exception {
		// TODO Auto-generated method stub
		int code = 0;
		System.out.println("RetryTemplateImpl被调用,时间："+LocalTime.now());
		if (code==0){
		    throw new Exception("业务执行失败情况！");
		}
		System.out.println("RetryTemplateImpl执行成功！");

		return 200;
	}
	
	class RecoverImpl implements Recover{

		@Override
		public String recover() {
			// TODO Auto-generated method stub
			System.out.println("重试失败   恢复逻辑，记录日志等操作");
			return null;
		}
	}
	
}
