package org.jackdking.login.jwt.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class UserDetail implements Serializable{
	
	private String username;
	private String password;
	
	private List<String> roles;

}
