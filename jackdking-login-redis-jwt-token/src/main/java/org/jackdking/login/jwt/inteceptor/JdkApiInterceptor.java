package org.jackdking.login.jwt.inteceptor;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jackdking.login.jwt.utils.CookieUtil;
import org.jackdking.login.jwt.utils.JwtTokenProvider;
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
    private JwtTokenProvider jwtTokenProvider;

    /**
     * 拦截请求，在controller调用之前
     * 返回 false：请求被拦截，返回
     * 返回 true ：请求OK，可以继续执行，放行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        String jwtToken = jwtTokenProvider.resolveTokenFromCookie();//获取用户cookies 中的jwttoken
        logger.info("解析得到的token值：{}",jwtToken);
        //放开登入接口
//        String uri = request.getRequestURI();
//        logger.info("请求uri:" + uri);
//        
//        if(uri.equals("loginCheck"))
//        	return true;
//         
        //用户id和token都不为空
        if (!StringUtils.isEmpty(jwtToken)) {
        	
        	//根据userid生成唯一key从redis中查出唯一token
        	
            
            //如果唯一token为空 ，则拦截url重定向到登入页面
            if (!jwtTokenProvider.validateToken(jwtToken)) {
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
