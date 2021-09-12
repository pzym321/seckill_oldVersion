package com.pang.seckill.vo;

import com.pang.seckill.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Pang ZhengYanMing
 * @Date: 2021/09/11/15:37
 * @Description: login parms
 */
@Data
public class LoginVo {
    @NotNull
    @IsMobile
    private String mobile;
    @NotNull
    @Length(min=32) //md5加密后的密码为32位
    private String password;
}
