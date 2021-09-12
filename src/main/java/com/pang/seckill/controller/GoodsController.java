package com.pang.seckill.controller;

import com.pang.seckill.pojo.User;
import com.pang.seckill.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Pang ZhengYanMing
 * @Date: 2021/09/11/21:30
 * @Description: 商品控制层
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IUserService userService;

    /**
     * 跳转商品列表页面
     * @param
     * @param model
     * @param
     * @return
     */
    @RequestMapping("/toList")
    public String toList(Model model,User user){
        model.addAttribute("user",user);
        return "goodsList";
    }
//    public String toList1(HttpServletRequest request, HttpServletResponse response, Model model, @CookieValue("userTicket") String ticket){
//        if (StringUtils.isEmpty(ticket)){
//            return "login";
//        }
//        User user = userService.getUserByCookie(ticket, request, response);
//        if (null==user){
//            return "login";
//        }
//        model.addAttribute("user",user);
//        return "goodsList";
//    }
}
