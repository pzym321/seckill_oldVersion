package com.pang.seckill.controller;

import com.pang.seckill.pojo.Order;
import com.pang.seckill.pojo.SeckillMessage;
import com.pang.seckill.pojo.SeckillOrder;
import com.pang.seckill.pojo.User;
import com.pang.seckill.rabbitmq.MQSender;
import com.pang.seckill.service.IGoodsService;
import com.pang.seckill.service.IOrderService;
import com.pang.seckill.service.ISeckillOrderService;
import com.pang.seckill.utils.JsonUtil;
import com.pang.seckill.vo.GoodsVo;
import com.pang.seckill.vo.RespBean;
import com.pang.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Pang ZhengYanMing
 * @Date: 2021/09/13/14:07
 * @Description: 秒杀
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MQSender mqSender;
    @Autowired
    private ISeckillOrderService seckillOrderService;

    private Map<Long,Boolean> emptyStockMap=new HashMap<>();

    /**
     *获取秒杀结果
     * @param user
     * @param goodsId
     * @return orderId:成功 -1：失败 0：排队中
     */
    @RequestMapping(value = "/result",method = RequestMethod.GET)
    @ResponseBody
    public RespBean getResult(User user,Long goodsId){
        if (user==null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        Long orderId=seckillOrderService.getResult(user,goodsId);
        return RespBean.success(orderId);
    }

    /**
     * jmeter 1000线程程循环10次测3组，样本数30000，goodsId=1
     * linux端(1核2G)优化前QPS:170
     *
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/doSeckill")
    @ResponseBody
    public RespBean doSeckill (Model model, User user, Long goodsId){
        if (user==null){
            return RespBean.success();
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //判断是否重复抢购
        SeckillOrder seckillOrder =
                (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if (seckillOrder!=null){
           return RespBean.error(RespBeanEnum.REPEAT_ERROR);
        }
        //通过内存标记，减少对redis的访问，就像冒泡排序的flag一样
        if (emptyStockMap.get(goodsId)){
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        //预减库存
        Long stock = valueOperations.decrement("seckillGoods:" + goodsId);
        if (stock<0){
            emptyStockMap.put(goodsId,true);
            valueOperations.increment("seckillGoods:" + goodsId);//再加一次 不然库存-1不好看
           return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        SeckillMessage seckillMessage = new SeckillMessage();
        mqSender.sendSeckillMessage(JsonUtil.Object2JsonStr(seckillMessage));
        return RespBean.success(0);


        /*
        model.addAttribute("user",user);
        //判断库存
        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
        if (goods.getStockCount()<1){
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return "seckillFail";
        }
        //判断是否重复抢购
        SeckillOrder seckillOrder =
                (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if (seckillOrder!=null){
            model.addAttribute("errmsg",RespBeanEnum.REPEAT_ERROR.getMessage());
            return "seckillFail";
        }
        Order order=orderService.seckill(user,goods);
        model.addAttribute("order",order);
        model.addAttribute("goods",goods);
        return "orderDetail";
         */

    }

    /**
     * 系统初始化，把商品库存加载到redis
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> list=goodsService.findGoodsVo();
        if (CollectionUtils.isEmpty(list)){
            return;
        }
        list.forEach(goodsVo -> {
            redisTemplate.opsForValue().set("seckillGoods:"+goodsVo.getId(),goodsVo.getStockCount());
            emptyStockMap.put(goodsVo.getId(),false);
        });
    }
}
