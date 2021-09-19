package com.pang.seckill.controller;

import com.pang.seckill.pojo.User;
import com.pang.seckill.service.IGoodsService;
import com.pang.seckill.service.IUserService;
import com.pang.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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
    @Autowired
    private IGoodsService goodsService;

    /**
     * 跳转商品列表页面
     *
     * jmeter 1万线程循环3次*3
     * mac端优化前QPS:864
     * linux端优化前QPS:207
     *
     * @param
     * @param model
     * @param
     * @return
     */
    @RequestMapping("/toList")
    public String toList(Model model,User user){
        model.addAttribute("user",user);
        model.addAttribute("goodsList",goodsService.findGoodsVo());
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

    @RequestMapping("toDetail/{goodsId}")
    public String toDetail(@PathVariable("goodsId")Long id,Model model,User user){
        //秒杀状态
        int seckillStatus=0;
        //秒杀倒计时
        int remainSecond=0;
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(id);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        //秒杀未开始
        if(startDate.after(nowDate)){
            remainSecond=(int)(startDate.getTime()-nowDate.getTime())/1000;
        }else if (nowDate.after(endDate)){
            //秒杀已结束
            seckillStatus=2;
            remainSecond=-1;
        }else{
            seckillStatus=1;//秒杀进行中
            remainSecond=0;
        }
        model.addAttribute("remainSeconds",remainSecond);
        model.addAttribute("seckillStatus",seckillStatus);
        model.addAttribute("user",user);
        model.addAttribute("goods",goodsVo);
        return "goodsDetail";
    }
}
