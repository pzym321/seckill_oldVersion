package com.pang.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pang.seckill.pojo.User;
import com.pang.seckill.vo.LoginVo;
import com.pang.seckill.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Pang ZhengYanMing
 * @since 2021-09-11
 */
public interface IUserService extends IService<User> {

    RespBean doLogin(LoginVo vo, HttpServletRequest request, HttpServletResponse response);
}
