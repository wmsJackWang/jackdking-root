package org.jackdking.controller_samples.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * 后端控制页面跳转 —— 重定向测试，转发测试
 * 
 * 测试indexOne跳转到indexTwo
 * 
 * 浏览器中输入localhost:8080/indexOne/indexOne
 * 
 */
@Controller
@RequestMapping("/route")
public class RedirectTestController {
	
	@RequestMapping("/indexOne/{param}")
	@ResponseBody
	public String indexOne(HttpServletRequest request , HttpServletResponse response) {
		//转发到indexTwo
		System.out.println("indexOne");
		try {
			request.getRequestDispatcher("/indexTwo/indexTwo").forward(request,response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/indexTwo/{param}")
	@ResponseBody
	public String indexTwo(HttpServletRequest request , HttpServletResponse response,@PathVariable String param) {
		
		
		return param;
	}
	
	@RequestMapping("/indexThree/{param}")
	@ResponseBody
	public String indexThree(HttpServletRequest request , HttpServletResponse response)  {

		System.out.println("indexTwo");
		try {
			response.sendRedirect("redirect:localhost:8080/indexTwo/indexTwo");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
