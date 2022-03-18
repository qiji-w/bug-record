package com.bug.bugrecordbackend.util;

import java.security.MessageDigest;

/**
 * 密码加密工具
 */
public class PasswordUtil {
    public static final String encrypt(String password, String salt){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            byte[] bytes = md.digest((password+salt).getBytes("utf-8"));
            return toHex(bytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 二进制的加密字符串转成16进制 压缩存储空间
     * @param bytes
     * @return
     */
    private static String toHex(byte[] bytes) {
        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder result = new StringBuilder(bytes.length * 2);
        for (int i=0; i<bytes.length; i++) {
            //1byte=8bit，分别将前4位和后4位转成16进制
            //>>:按位右移；&：按位与
            result.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);  //前4位
            result.append(HEX_DIGITS[bytes[i] & 0x0f]);   //后4位
        }
        return result.toString();
    }
}
