package com.pang.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pang.seckill.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Pang ZhengYanMing
 * @since 2021-09-11
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
