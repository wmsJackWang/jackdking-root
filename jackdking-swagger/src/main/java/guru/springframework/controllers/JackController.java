package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/jack")
@Api(value="jackTest", description="测试注解@controller")
public class JackController {

	
	 	@RequestMapping(value = "/test", method= RequestMethod.GET, produces = "application/json")
	 	@ApiOperation(value = "rediret to hello ")
		public String list(Model model){
	 		
		    return "hello";
		}
	
}
