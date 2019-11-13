package com.example.demo.util;

/**
 * Created by e-shenbin1 on 2018/11/1.
 */
public class NameUtil {
    /**
     * Java文件操作 获取文件扩展名
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename.toLowerCase();
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename.toLowerCase();
    }

    public static void main(String[] args) {
        String str = "AAAbb.jpg";
        System.out.println(getExtensionName(str).toLowerCase());
        System.out.println(getFileNameNoEx(str).toUpperCase());
    }
}
