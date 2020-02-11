package org.jackdking.retry.jdkdkingannotation.controller;

import javax.annotation.Resource;

import org.jackdking.retry.jdkdkingannotation.service.impl.AnnotationServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnnotationController {
	
	@Resource(name="AnnotationServiceImpl")
	private AnnotationServiceImpl annotationServiceImpl;
	
	@GetMapping("/testAnnotationRetry")
	public String testAnnotationRetry() {
		
		
		try {
			annotationServiceImpl.testService();
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "SUCCESS";
		
	}

}
