package org.jackdking.retry.retrytemplate.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.jackdking.retry.retrytemplate.service.RetryTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RetryTemplateController {

	@Resource(name="serviceone")
	private RetryTemplate retryTemplate;
	
	@GetMapping("/testRetryTemplate")
	public String testRetryTemplate() {
		int code=0;
		
		//提交逻辑是个异步提交，异步线程池  执行重试逻辑和业务逻辑。
		//和注解重试还不一样。
		//那这个和spring-retry 是一样的方式吗，可以通过spring-try模块的日志看出。
		// 结论：spring-try 没有使用异步的方式。
        retryTemplate.submit(Executors.newSingleThreadExecutor());
        return "success";
		
	}
	
}
