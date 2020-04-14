package org.jackdking.controller_samples.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
