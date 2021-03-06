package com.pang.seckill.controller;

import com.pang.seckill.service.IUserService;
import com.pang.seckill.vo.LoginVo;
import com.pang.seckill.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Pang ZhengYanMing
 * @Date: 2021/09/11/15:10
 * @Description: 登录
 */
@Controller
@Slf4j
public class LoginController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/")
    public String index(){
        return "login";
    }

    @RequestMapping("/login/toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login/doLogin")
    @ResponseBody
    public RespBean doLogin(@Valid LoginVo vo,HttpServletRequest request,HttpServletResponse response){
        //log.info("{}",vo);
       return userService.doLogin(vo,request,response);
    }
}
