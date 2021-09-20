package com.pang.seckill.controller;

import com.pang.seckill.pojo.User;
import com.pang.seckill.service.IGoodsService;
import com.pang.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    private RedisTemplate redisTemplate;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 跳转商品列表页面 页面缓存
     * @param
     * @param model
     * @param
     * @return
     */
    @RequestMapping(value = "/toList",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toList(Model model,User user,HttpServletRequest request,HttpServletResponse response){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsList");
        if (!StringUtils.isEmpty(html)){
            return html;
        }
        model.addAttribute("user",user);
        model.addAttribute("goodsList",goodsService.findGoodsVo());
        WebContext context = new WebContext(request, response,
                request.getServletContext(), request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", context);
        if (!StringUtils.isEmpty(html)){
            valueOperations.set("goodsList",html,1, TimeUnit.MINUTES);
        }
        return html;
    }

    /**
     * 页面缓存
     * @param id
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "toDetail/{goodsId}",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toDetail(@PathVariable("goodsId")Long id,Model model,User user,HttpServletRequest request,HttpServletResponse response){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsDetail:" + id);
        if (!StringUtils.isEmpty(html)){
            return html;
        }
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

        WebContext context = new WebContext(request, response,
                request.getServletContext(), request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", context);
        if (!StringUtils.isEmpty(html)){
            valueOperations.set("goodsDetail:"+id,html,1, TimeUnit.MINUTES);
        }
        return html;
    }
}
