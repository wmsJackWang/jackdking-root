package org.jackdking.controller_samples.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
			//测试  转发  跳转失败 测试的跳转地址 localhost:8080/jdk/route/indexFinal/indexTwo
			//测试  转发  跳转成功 测试的跳转地址 /route/indexFinal/indexTwo  ，这是同一个根目录/jdk下的地址
			request.getRequestDispatcher("/route/indexFinal/indexFinal").forward(request,response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/indexOne1/{param}")
	@ResponseBody
	public String indexOne1(HttpServletRequest request , HttpServletResponse response,@PathVariable String param) {
		//转发到indexTwo
		System.out.println("indexOne1:" + param);
		try {

			//设置param值到这个请求的request中。
			request.setAttribute("key", param);
			//测试  转发  跳转失败 测试的跳转地址 localhost:8080/jdk/route/indexFinal/indexTwo
			//测试  转发  跳转成功 测试的跳转地址 /route/indexFinal/indexTwo  ，这是同一个根目录/jdk下的地址
			//测试  转发  跳转失败 测试的跳转地址 http://localhost:8080/jdk/route/indexFinal/indexFinal
			//转发的路径必须是同一web容器下的url，因为本项目根路径是jdk，所以所有的转发路径前面都会添加/jdk/
			
			//测试  转发  跳转成功 测试的跳转地址  /routetest/url/url ，因此只要是同一个web根目录下就能实现转发.
			request.getRequestDispatcher("/routetest/url/url").forward(request,response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//这个方法作为其他所有方法跳转的最终接口
	@RequestMapping("/indexFinal/{param}")
	@ResponseBody
	public String indexFinal(HttpServletRequest request , HttpServletResponse response,RedirectAttributes redirectAttributes,@PathVariable String param) {
//		
		System.out.println("indexFinal:" + param);
		Object result = request.getAttribute("key");
		if(result==null)
			System.out.println("request中没有key属性");
		else
			System.out.println("request中有key属性，key="+ (String)result);
		
		Map<String, String> rediretAtt  = (Map<String, String>) redirectAttributes.getFlashAttributes();
		System.out.println("重定向传来的参数值: "+rediretAtt.get("key"));
		
		return param;
	}
	
	@RequestMapping("/indexTwo/{param}")
//	@ResponseBody
	public String indexTwo(HttpServletRequest request , HttpServletResponse response , @PathVariable String param)  {

		System.out.println("indexTwo:" + param);
		
		//设置param值到这个请求的request中。
		request.setAttribute("key", "key");

		//第一次测试  跳转成功
		//根路径 是 /jdk, 重定向是发生在根目录下的
		return "redirect:http://localhost:8080/jdk/route/indexFinal/indexTwo";
		
	}
	
	
	/*
	 * 总结 ： response重定向方式 没成功过，原因可能是根目录是/jdk的原因
	 * 
	 * 使用return直接跳转，跳转的是同一个层路径下，因此这里的根目录就不需要添加了，跳转的路径中会自带/jdk
	 * 
	 */
	
	@RequestMapping("/indexThree/{param}")
//	@ResponseBody
	public String indexThree(HttpServletRequest request , HttpServletResponse response , @PathVariable String param)  {

		System.out.println("indexThree:" + param);
		
		//设置param值到这个请求的request中。
		request.setAttribute("key", "key");

		
		//第一次测试  跳转成功
		//根路径 是 /jdk, 重定向是发生在根目录下的
		return "redirect:/route/indexFinal/indexFinal";
	}

	/*
	 *  成功跳转到其他网站地址：如百度https://www.baidu.com  http://www.baidu.com
	 *  注意：必须添加https:// 或  http://
	 */
	@RequestMapping("/indexFour/{param}")
	public String indexFour(HttpServletRequest request , HttpServletResponse response , @PathVariable String param) {
		
		
		return "redirect:http://www.baidu.com";
	}
	
	
	// 跳转 页面  和  接口 的 转发 类似 
	// thyleaf前端模板框架下，index默认的都是html模板
	@RequestMapping("/indexFive/{name}")
	public ModelAndView  indexFive(HttpServletRequest request , HttpServletResponse response , @PathVariable String name) {
		
		ModelAndView view = new ModelAndView();
		view.setViewName("index");
		
		return view;
	}

	/*
	 *  1. jsp模板和thyleaf的模板共存 ，需要设置前端解析器的配置bean : ViewResolverConfiguration
	 *  2. jsp模板一样也需要设置 依赖jar ，见pom文件
	 *  3. /WEB-INF/resources/对于springboot而言是根目录，所以prefix不需要加上前缀
	 *  4. 上一个方法和这个方法 是两种前端模板的测试成功案例
	 *  
	 */
	@RequestMapping("/indexSix/{name}")
	public String indexSix(HttpServletRequest request , HttpServletResponse response , @PathVariable String name) {
		
		
		return "jsp/test";
	}

	//转发成功
	@RequestMapping("/indexSeven/{name}")
	public String indexSeven(HttpServletRequest request , HttpServletResponse response , @PathVariable String name) {
		
		try {
			response.sendRedirect("http://www.baidu.com");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	//转发成功
	@RequestMapping("/indexEight/{name}")
	public String indexEight(HttpServletRequest request , HttpServletResponse response , @PathVariable String name) {
		
		try {
			request.setAttribute("key", "key");
			response.sendRedirect("/jdk/route/indexFinal/indexFinal");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	//转发失败 , 页面跳转只能是modelview以及返回字符串
	@RequestMapping("/indexNight/{name}")
	public String indexNight(HttpServletRequest request , HttpServletResponse response , @PathVariable String name) {
		
		try {
			request.setAttribute("key", "key");
			response.sendRedirect("/jsp/test");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	//解决重定向  参数丢失的问题
	@RequestMapping("/indexTen/{name}")
	public String indexTen(HttpServletRequest request , HttpServletResponse response , RedirectAttributes redirectAttributes ,@PathVariable String name) {
		

		try {
//			request.setAttribute("key", "key");
			redirectAttributes.addFlashAttribute("key", "key");
			response.sendRedirect("/jdk/route/indexFinal/indexFinal");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
