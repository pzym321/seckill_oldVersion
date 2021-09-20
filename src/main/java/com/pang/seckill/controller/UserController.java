package com.pang.seckill.controller;


import com.pang.seckill.pojo.User;
import com.pang.seckill.vo.RespBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Pang ZhengYanMing
 * @since 2021-09-11
 */
@Controller
@RequestMapping("/user")
public class UserController {
    /**
     * 用户信息--测试
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/info")
    public RespBean info(User user){
        return RespBean.success(user);
    }
}
