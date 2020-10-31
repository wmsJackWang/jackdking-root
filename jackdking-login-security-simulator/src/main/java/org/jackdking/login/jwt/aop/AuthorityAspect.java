package org.jackdking.login.jwt.aop;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jackdking.login.jwt.anoation.PreAuthority;
import org.jackdking.login.jwt.domain.UserDetail;
import org.jackdking.login.jwt.utils.RestResponseBo;
import org.jackdking.login.jwt.utils.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Aspect
@Component
public class AuthorityAspect {

	
    @Around("@annotation(preAuthority)")
    public Object  preAuthority(ProceedingJoinPoint proceedingJoinPoint , PreAuthority preAuthority){
    	
//        DataSourceType curType = dbType.value();
    	String [] authority = preAuthority.roles();
    	System.out.println("print: "+authority[0]);
    	
    	//判断权限逻辑
    	if(!ObjectUtils.isEmpty(authority))
    	{
    		boolean isThrough = false;
    		UserDetail details = (UserDetail)SecurityContextHolder.get();
    		List<String> roles = details.getRoles();
    		for(String s : authority)
    			if(roles.contains(s)||roles.contains("admin"))//如果 权限中有一个是 资源权限则通过 ,管理员也通过
    				isThrough = true;
    		if(!isThrough)
                return RestResponseBo.fail("权限不够");
    		
    	}
    	
    	//业务方法
        //访问目标方法的参数：
        Object[] args = proceedingJoinPoint.getArgs();
    	Object result = null;
		try {
			result = proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result;
    	
    	
    	
    	
    	
    	
       

    }
	
}
