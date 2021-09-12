package com.pang.seckill.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Pang ZhengYanMing
 * @Date: 2021/09/11/15:53
 * @Description: 手机号码校验
 */
public class ValidatorUtil {
    private static final Pattern mobiel_pattern = Pattern.compile("^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$");

    /**
     * 手机号码是否合规
     * @param mobile
     * @return 是返回ture，不是为false
     */
    public static boolean isMobile(String mobile){
        if(StringUtils.isEmpty(mobile)){
            return false;
        }
        Matcher matcher = mobiel_pattern.matcher(mobile);
        return matcher.matches();
    }
}
