package com.pang.seckill.service.impl;

import com.pang.seckill.pojo.Order;
import com.pang.seckill.mapper.OrderMapper;
import com.pang.seckill.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Pang ZhengYanMing
 * @since 2021-09-13
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
