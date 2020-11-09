package org.jackdking.security.rest;

import org.jackdking.security.model.User;
import org.jackdking.security.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserRestController {

   private final UserService userService;

   public UserRestController(UserService userService) {
      this.userService = userService;	
   }

   @GetMapping("/user")
   public ResponseEntity<User> getActualUser() {
      return ResponseEntity.ok(userService.getUserWithAuthorities().get());
   }
   
   
   @RequestMapping("/info1")
   public String info1() {
	   return "{'interface':'/info1','msg':'Success'}";
   }
   
   @RequestMapping("/info2")
   public String info2() {
	   return "{'interface':'/info2','msg':'Success'}";
   }
   
}
