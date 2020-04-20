package org.jackdking.controller_samples.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * 在pom文件中切换<parent>...</parent>,选择springboot版本来测试这个controller
 * 
 * springboot有两个版本 ，分别是1.4.0和 2.1.0版本
 * 
 * 测试结果： 2.1.0版本 第一个api能完整获取到param的值
 *         1.4.0版本 第一个api获取不到完整的param值
 *         
 *         
 * 1.4.0版本获取完整值的方式  是第二个api，它能获取完整的param值
 */
@Controller
@RequestMapping("/route")
public class SampleOneController {
	
	@ResponseBody
	@RequestMapping("/url/{param}")
	public String getUrlParam(@PathVariable String param) {
		
		return param;
	}
	
	@ResponseBody
	@RequestMapping("/urlRegx/{param:[_a-zA-Z0-9\\.]+}")
	public String getUrlParamRegx(@PathVariable String param) {
		
		return param;
	}
}
