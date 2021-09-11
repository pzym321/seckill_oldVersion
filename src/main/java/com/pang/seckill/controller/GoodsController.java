package com.pang.seckill.controller;

import com.pang.seckill.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Pang ZhengYanMing
 * @Date: 2021/09/11/21:30
 * @Description:
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    /**
     * 跳转商品列表页面
     * @param session
     * @param model
     * @param ticket
     * @return
     */
    @RequestMapping("/toList")
    public String toList(HttpSession session, Model model,@CookieValue("userTicket") String ticket){
        if (StringUtils.isEmpty(ticket)){
            return "login";
        }
        User user=(User)session.getAttribute(ticket);
        if (null==user){
            return "login";
        }
        model.addAttribute("user",user);
        return "goodsList";
    }
}
