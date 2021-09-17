package com.pang.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pang.seckill.exception.GlobalException;
import com.pang.seckill.mapper.UserMapper;
import com.pang.seckill.pojo.User;
import com.pang.seckill.service.IUserService;
import com.pang.seckill.utils.CookieUtil;
import com.pang.seckill.utils.MD5Util;
import com.pang.seckill.utils.UUIDUtil;
import com.pang.seckill.vo.LoginVo;
import com.pang.seckill.vo.RespBean;
import com.pang.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Pang ZhengYanMing
 * @since 2021-09-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public RespBean doLogin(LoginVo vo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = vo.getMobile();
        String password = vo.getPassword();
        //参数校验
//        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//        }
//        if(!ValidatorUtil.isMobile(mobile)){
//            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//        }
        //根据手机获取用户
        User user = userMapper.selectById(mobile);
        if (null == user) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //判断密码是否正确
        //前端一次md5加密后的密码再次进行MD5加密，然后和数据库密码对比，不相同的话登录错误
        if (!MD5Util.formPassToDBPass(password, user.getSlat()).equals(user.getPassword())) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //通过uuid工具类生成cookie
        String ticket = UUIDUtil.uuid();
        System.out.println(ticket);
        //region 作废用户信息存入session方法，采用存入redis
        //把cookie作为key，user对象作为value存入session里
        HttpSession session = request.getSession();
        session.setAttribute(ticket, user);
        session.setMaxInactiveInterval(3600*10);//10 hours
        //endregion
        redisTemplate.opsForValue().set("user:" + ticket, user);
        //设置cookie
        CookieUtil.setCookie(request, response, "userTicket", ticket);
        return RespBean.error(RespBeanEnum.SUCCESS);
    }

    @Override
    public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(userTicket)) {
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
        if (user != null) {
            CookieUtil.setCookie(request, response, "userTicket", userTicket);
        }
        return user;
    }

}
