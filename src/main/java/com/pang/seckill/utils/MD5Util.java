package com.pang.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;
import sun.security.provider.MD5;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Pang ZhengYanMing
 * @Date: 2021/09/11/13:02
 * @Description: MD5工具类
 */
@Component
public class MD5Util {
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    /**
     * 第一次加密
     */
    public static String inputPassToFromPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 第二次加密
     */
    public static String formPassToDBPass(String fromPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + fromPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 封装前两次加密
     * 实际被调用方法
     */
    public static String inputPassToDBPass(String inputPass, String salt) {
        String fromPass = inputPassToFromPass(inputPass);
        String dbpass = formPassToDBPass(fromPass, salt);
        return dbpass;
    }

    public static void main(String[] args) {
            System.out.println(inputPassToFromPass("123456"));
            System.out.println(formPassToDBPass("d3b1294a61a07da9b49b6e22b2cbd7f9","1a2b3c4d"));
            System.out.println(inputPassToDBPass("123456","1a2b3c4d"));
        }
}
