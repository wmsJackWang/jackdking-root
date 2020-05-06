package org.jackdking.shardjdbcyaml.controller;
 
import org.jackdking.shardjdbcyaml.bean.TransInfo;
import org.jackdking.shardjdbcyaml.service.TransInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody; 

 
@Controller
@ResponseBody
@RequestMapping("/jsonController")
public class MyController {
	
	@Autowired
	TransInfoService infoService;

	/*
	 * 
create table TransInfo0( id bigint primary key auto_increment, userid varchar(20) not null, orderid bigint(20)   unsigned NOT NULL );
	 */
	@PostMapping(value="/transTB")
//	@RequestMapping(value="/transOneParam" ,method = RequestMethod.POST , produces= "application/json; charset=utf-8")
	@ResponseBody
	public String createTransDiffTB(TransInfo transinfo) {

		System.out.println("订单参数数据:"+transinfo.toString());
		
		infoService.save(transinfo);
		
		return "SUCCESS";
	}
	
	
	@PostMapping(value="/trans")
//	@RequestMapping(value="/transOneParam" ,method = RequestMethod.POST , produces= "application/json; charset=utf-8")
	@ResponseBody
	public String createTrans(TransInfo transinfo) {
		
		System.out.println("订单参数数据:"+transinfo.toString());
		return "SUCCESS";
	}
	
	@RequestMapping(value="/getTrans")
	public String getTransInfo() {
		
		TransInfo result = infoService.get(1);
		int[] array = new int[]{1,2,3,4,5,6,7};
		return "SUCCESS";
	}

}
