package org.jackdking.login.jwt.inteceptor;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jackdking.login.jwt.utils.CookieUtil;
import org.jackdking.login.jwt.utils.RedisOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class JdkApiInterceptor implements HandlerInterceptor {
	
	
    private static final Logger logger = LoggerFactory.getLogger(JdkApiInterceptor.class);
    
    
    public static final String USER_REDIS_SESSION = "user_redis_session";

    @Autowired
    public RedisOperator redis;

    /**
     * 拦截请求，在controller调用之前
     * 返回 false：请求被拦截，返回
     * 返回 true ：请求OK，可以继续执行，放行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        String userToken = CookieUtil.getCookie("userToken");//获取用户cookies
        String userName = CookieUtil.getCookie("userName");
        
        //放开登入接口
//        String uri = request.getRequestURI();
//        logger.info("请求uri:" + uri);
//        
//        if(uri.equals("loginCheck"))
//        	return true;
//        
        logger.info("拦截UserToken：" + userToken + " ======= 拦截UserId：" + userName);
        //用户id和token都不为空
        if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(userToken)) {
        	
        	//根据userid生成唯一key从redis中查出唯一token
            String uniqueToken = redis.get(USER_REDIS_SESSION + ":" + userName);
            logger.info("拦截uniqueToken：" + uniqueToken);
            
            //如果唯一token为空 ，则拦截url重定向到登入页面
            if (StringUtils.isEmpty(uniqueToken)) {
                response.sendRedirect("/login");
                returnErrorResponse(response, "请登录...");

                return false;
            }
        //用户id和token有一个为空，则重定向登入页面
        } else {
            response.sendRedirect("/login");
            returnErrorResponse(response,"请登录...");
            return false;
        }

        return true;
    }

    /**
     * 把拦截数据返回给前端
     * @param response
     * @param result
     * @throws IOException
     */
    private void returnErrorResponse(HttpServletResponse response, String result) throws IOException {
        response.setContentType("text/json");
        response.setCharacterEncoding("utf-8");
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            out.write(result.getBytes("utf-8"));
            out.flush();
        } finally {
            if(out!=null){
                out.close();
            }
        }
    }

    /**
     * 请求controller之后，渲染视图之前
     */
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {

    }

    /**
     * 请求controller之后，视图渲染之后
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {

    }
}
