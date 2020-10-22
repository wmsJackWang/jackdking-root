package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderController {
	
	
	@RequestMapping("/create")
	public String createOrder() {
		
		return "";
	}

}
