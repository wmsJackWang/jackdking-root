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
	public String indexOne(HttpServletRequest request , HttpServletResponse response,@PathVariable String param) {
		//转发到indexTwo
		System.out.println("indexOne:" + param);
		try {

			//设置param值到这个请求的request中。
			request.setAttribute("key", param);
			//测试  转发  跳转失败
			request.getRequestDispatcher("localhost:8080/jdk/route/indexTwo/indexTwo").forward(request,response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/indexTwo/{param}")
	@ResponseBody
	public String indexTwo(HttpServletRequest request , HttpServletResponse response,@PathVariable String param) {
//		
		System.out.println("indexTwo:" + param);
		Object result = request.getAttribute("key");
		if(result==null)
			System.out.println("request中没有key属性");
		else
			System.out.println("request中有key属性");
		
		return param;
	}
	
	
	/*
	 * 总结 ： response重定向方式 没成功过，原因可能是根目录是/jdk的原因
	 */
	
	@RequestMapping("/indexThree/{param}")
//	@ResponseBody
	public String indexThree(HttpServletRequest request , HttpServletResponse response , @PathVariable String param)  {

		System.out.println("indexThree:" + param);
		
		//设置param值到这个请求的request中。
		request.setAttribute("key", "key");
		
//		try {			
			//可以重定向到其他网站  
			//目前没有跳转成功  
			//第二次重测  失败
//			response.sendRedirect("redirect:http://localhost:8080/jdk/route/indexTwo/indexTwo");
			
			//冲定向到 当前WEB应用程序的根目录
			//目前没有跳转成功
//			response.sendRedirect("redirect:/jdk/route/indexTwo/indexTwo");
			
			//第一次 测试 重定向   跳转失败
//			response.sendRedirect("redirect:/route/indexTwo/indexTwo");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//第一次 测试 跳转失败  
		//失败原因   url前面必须有http或者https，否则失败
		//第二次 测试 跳转成功
//		return "redirect:http://localhost:8080/jdk/route/indexTwo/indexTwo";
		
		//第一次测试  跳转成功
		//根路径 是 /jdk, 重定向是发生在根目录下的
		return "redirect:/route/indexTwo/indexTwo";
		
//		return null;
	}
	
	
	public static void main(String[] args) {
		
		
		
	}
	
}
