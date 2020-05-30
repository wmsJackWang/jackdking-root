package org.jackdking.retry.retrytemplate.controller;

import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.jackdking.retry.retrytemplate.JdkExcutor;
import org.jackdking.retry.retrytemplate.service.RetryTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RetryTemplateController {
	

	private static final Logger log = LoggerFactory.getLogger(RetryTemplateController.class);

	@Resource(name="serviceone")
	private RetryTemplate retryTemplate;
	
	@Autowired
	private RetryTemplate template;
	
	@GetMapping("/testRetryTemplate")
	@ResponseBody
	public String testRetryTemplate() {
		int code=0;
		
		//提交逻辑是个异步提交，异步线程池  执行重试逻辑和业务逻辑。
		//和注解重试还不一样。
		//那这个和spring-retry 是一样的方式吗，可以通过spring-try模块的日志看出。
		// 结论：spring-try 没有使用异步的方式。
		
		try {
			log.info("开始执行业务");
			template.doBiz();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//提交异步业务重试
	        retryTemplate.submit(JdkExcutor.executorService);
		}
		
        return "异步重试是不知道结果如何的，需要等待回调通知处理结果";
		
	}
	
}
