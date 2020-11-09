package com.jackdking.security.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptUtil {

    /**
     * @Author: Galen
     * @Description: BCrypt加密
     * @Date: 2019/3/21-18:07
     * @Param: [psw]
     * @return: java.lang.String
     **/
    public final static String encode(String psw) {
         BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
         return encoder.encode(psw);
    }
    
    public static void main(String[] args) {
		System.out.println(encode("admin"));
	}
}
