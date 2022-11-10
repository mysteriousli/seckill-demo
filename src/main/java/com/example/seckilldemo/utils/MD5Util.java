package com.example.seckilldemo.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;


@Component
public class MD5Util {
    // md5加密
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";
    //md5(pass+salt)
    public static String inputPassToFromPass(String inputPass) {
        String str = ""+salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }
    //md5(middel+salt)
    public static String fromPassToDBPass(String fromPass,String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + fromPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    //md5(md5(pass+salt)+salt)
    public static String inputPassToDBPass(String inputPass,String salt) {
        String fromPass = inputPassToFromPass(inputPass);
        String dbPass = fromPassToDBPass(fromPass, salt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFromPass("123"));
        System.out.println(fromPassToDBPass("c38dc3dcb8f0b43ac8ea6a70b5ec7648", "1a2b3c4d"));
        System.out.println(inputPassToDBPass("123", "1a2b3c4d"));
    }
}
