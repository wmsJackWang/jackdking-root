package com.example.demo.util;

/**
 * Created by e-shenbin1 on 2018/11/1.
 */
//import com.yxtech.sys.controller.MailController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2015/10/12.
 */
public class PathUtil {

    /**
     *
     * @param request
     * @return 返回结果类似于 “F:\workSpace\bookQr\src\main\webapp\”
     */
    public static String  getAppRootPath(HttpServletRequest request){
        //ServletActionContext.getServletContext().getRealPath("/")+"upload";
        return request.getSession().getServletContext().getRealPath("/");
    }

    /**
     *自定义文件保存路径
     * @param request
     */
//    public static String  getCustomRootPath(HttpServletRequest request){
//        String path = "";
//        Properties prop = new Properties();
//        InputStream in = MailController.class.getResourceAsStream("/config/jdbc.properties");
//        try {
//            prop.load(in);
//            path = prop.getProperty("FILE_PATH").trim();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return path;
//    }

    /**
     *
     * @param request
     * @return http://www.qh.com:8080/projectName
     */
    public static String  getHttpURL(HttpServletRequest request) {
        StringBuffer buff = new StringBuffer();
        buff.append("http://");
        buff.append(request.getServerName());
        buff.append(":");
        buff.append(request.getServerPort());
        buff.append(request.getContextPath());
        return buff.toString();
    }


}