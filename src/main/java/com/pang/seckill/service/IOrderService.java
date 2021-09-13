package com.pang.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pang.seckill.pojo.Order;
import com.pang.seckill.pojo.User;
import com.pang.seckill.vo.GoodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Pang ZhengYanMing
 * @since 2021-09-13
 */
public interface IOrderService extends IService<Order> {

    /**
     * 商品秒杀
     * @param user
     * @param goodsVo
     * @return
     */
    Order seckill(User user, GoodsVo goodsVo);
}
