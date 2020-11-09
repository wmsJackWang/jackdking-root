package com.jackdking.security.utils;

import com.jackdking.security.pojo.GalenResponse;

/**
 * @Author: Galen
 * @Date: 2019/3/6-16:25
 * @Description: 对象统一返回格式工具类
 * 应遵循：
 * 成功逻辑码  200
 * 登陆信息失效码 801
 * controller层错误码 401起499止
 * service层错误码 501起599止
 **/
public class ResponseUtils {

    public static GalenResponse SUCCESS() {
        return new GalenResponse(200, "SUCCESS", null);
    }

    public static GalenResponse SUCCESS(Object bean) {
        return new GalenResponse(200, "SUCCESS", bean);
    }

    public static GalenResponse SUCCESS(Object bean, long total) {
        return new GalenResponse(total, bean);
    }

    public static GalenResponse FAIL() {
        return new GalenResponse(400, "FAIL", null);
    }

    public static GalenResponse FAIL(String msg) {
        return new GalenResponse(400, msg, null);
    }

    public static GalenResponse build(Integer status, String msg) {
        return new GalenResponse(status, msg, null);
    }

    public static GalenResponse build(Object bean) {
        return new GalenResponse(200, "SUCCESS", bean);
    }

    public static GalenResponse build(Integer status, String msg, Object bean) {
        return new GalenResponse(status, msg, bean);
    }

    /**
     * 身份失效
     *
     * @return
     */
    public static GalenResponse invalid() {
        return new GalenResponse(801, "登录信息失效，请重新登录", null);
    }

}

