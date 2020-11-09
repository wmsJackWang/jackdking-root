package com.jackdking.security.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    // 获得MD5摘要算法的 MessageDigest 对象
    private static MessageDigest MESSAGE_DIGEST = null;


    private static char hexDigitsCode[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static MessageDigest getMdInst() {
        if (MESSAGE_DIGEST == null) {
            try {
                MESSAGE_DIGEST = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return MESSAGE_DIGEST;
    }

    /**
     * TODO: MD5加密,不可逆
     *
     * @return
     * @throws
     * @author zhaoxi
     * @time 2019/1/16 15:01
     * @params
     */
    public final static String encode(String s) {
        try {
            byte[] btInput = s.getBytes();
            // 使用指定的字节更新摘要
            getMdInst().update(btInput);
            // 获得密文
            byte[] md = getMdInst().digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigitsCode[byte0 >>> 4 & 0xf];
                str[k++] = hexDigitsCode[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
