package com.jackdking.rw.separation.utils;

public class StringUtils {

    public static boolean isBlank(String data) {
        return org.springframework.util.StringUtils.isEmpty(data) || data.trim().isEmpty();
    }
}
