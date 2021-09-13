package com.pang.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pang.seckill.mapper.GoodsMapper;
import com.pang.seckill.pojo.Goods;
import com.pang.seckill.service.IGoodsService;
import com.pang.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Pang ZhengYanMing
 * @since 2021-09-13
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public List<GoodsVo> findGoodsVo() {

        return goodsMapper.findGoodsVo();
    }

    @Override
    public GoodsVo  findGoodsVoByGoodsId(Long id) {
        return goodsMapper.findGoodsVoByGoodsId(id);
    }
}
