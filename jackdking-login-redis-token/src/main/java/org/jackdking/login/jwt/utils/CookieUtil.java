package org.jackdking.login.jwt.utils;

import cn.hutool.json.JSONUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * cookie操作
 * @author YI
 * @date 2018-7-20 11:31:33
 */
public class CookieUtil {
    public static Cookie[] getCookies() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Cookie[] c = request.getCookies();
        return c;
    }

    public static void showCookie() {
        Cookie[] c = getCookies();
        for (int i = 0; i < (c == null ? 0 : c.length); i++) {
            System.out.println("一条cookie____  name: " + c[i].getName() + "  || value: " + c[i].getValue());
        }
    }

    public static void saveCookie(Cookie cookie) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        response.addCookie(cookie);
    }

    /**
     * 添加cookie
     *
     * @param name
     * @param object
     */
    public static void addCookie(String name, Object object) {
        try {

            String v = URLEncoder.encode(JSONUtil.toJsonStr(object), "UTF-8");

            Cookie cookie = new Cookie(name, v);
            cookie.setPath("/");
            // 设置保存cookie最大时长
            cookie.setMaxAge(60*60);
            saveCookie(cookie);

        } catch (Exception e) {
            System.out.println(" -------添加cookie 失败！--------" + e.getMessage());
        }
    }

    /**
     * 获取cookie
     *
     * @param name
     * @param clazz
     * @return
     */
    public static <T> T getCookie(String name, Class<T> clazz) {
        try {

            Cookie[] cookies = getCookies();
            String v = null;
            for (int i = 0; i < (cookies == null ? 0 : cookies.length); i++) {
                if ((name).equalsIgnoreCase(cookies[i].getName())) {
                    v = URLDecoder.decode(cookies[i].getValue(), "UTF-8");
                }
            }
            if (v != null) {
                return JSONUtil.toBean(v, clazz);
            }
        } catch (Exception e) {
            System.out.println("------获取 clazz Cookie 失败----- " + e.getMessage());
        }
        return null;
    }

    /**
     * 获取cookie
     *
     * @param name
     * @return
     */
    public static String getCookie(String name) {
        try {

            Cookie[] cookies = getCookies();

            for (int i = 0; i < (cookies == null ? 0 : cookies.length); i++) {
                if ((name).equalsIgnoreCase(cookies[i].getName())) {
                    return URLDecoder.decode(cookies[i].getValue(), "UTF-8");
                }
            }
        } catch (Exception e) {
            System.out.println(" --------获取String cookie 失败--------   " + e.getMessage());
        }
        return null;
    }

    /**
     * 删除cookie
     *
     * @param name
     */
    public static void removeCookie(String name) {
        try {

            Cookie[] cookies = getCookies();

            for (int i = 0; i < (cookies == null ? 0 : cookies.length); i++) {
                if ((name).equalsIgnoreCase(cookies[i].getName())) {

                    Cookie cookie = new Cookie(name, "");
                    cookie.setPath("/");
                    // 设置保存cookie最大时长为0，即使其失效
                    cookie.setMaxAge(0);
                    saveCookie(cookie);
                }
            }

        } catch (Exception e) {
            System.out.println(" -------删除cookie失败！--------" + e.getMessage());
        }
    }
}
