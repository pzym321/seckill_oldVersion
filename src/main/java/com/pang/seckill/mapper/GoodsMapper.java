package com.pang.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pang.seckill.pojo.Goods;
import com.pang.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Pang ZhengYanMing
 * @since 2021-09-13
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 获取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();

    /**
     * 获取商品详情
     * @param id
     * @return
     */
    GoodsVo findGoodsVoByGoodsId(Long id);
}
