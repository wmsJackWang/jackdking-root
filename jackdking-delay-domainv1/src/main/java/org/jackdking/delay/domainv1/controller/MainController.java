package org.jackdking.delay.domainv1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {
	
	
	@RequestMapping("/check")
	public String heartBeatController() {
		log.info("heart beat check");
		return "SUCCESS";
	}

}
