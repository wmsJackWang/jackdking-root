package org.jackdking.retry.springretry.controller;

import org.jackdking.retry.springretry.service.TestSpringRetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringRetryController {

	 	@Autowired
	    TestSpringRetryService testSpringRetryServiceImpl;
	 
	    @GetMapping("/springRetry")
	    public String testRetry() throws Exception {
	        int code=0;

			System.out.println("执行业务发起逻辑的线程名："+Thread.currentThread().getName());
	        int result = testSpringRetryServiceImpl.retryServiceOne(code);
	        return "result："+result;
	    }
}
