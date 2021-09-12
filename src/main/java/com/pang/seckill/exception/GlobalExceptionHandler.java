package com.pang.seckill.exception;

import com.pang.seckill.vo.RespBean;
import com.pang.seckill.vo.RespBeanEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Pang ZhengYanMing
 * @Date: 2021/09/11/20:23
 * @Description: 全局异常处理类
 */
@RestControllerAdvice//加rest意味返回的是responseBody
public class GlobalExceptionHandler {

    //ExceptionHandler(Exception.class): 需要处理异常的类
    @ExceptionHandler(Exception.class)
    public RespBean ExceptionHandler(Exception e){
        if(e instanceof GlobalException){
            GlobalException ex = (GlobalException)e;
            return RespBean.error(ex.getRespBeanEnum());
        }else if(e instanceof BindException){
            BindException ex = (BindException)e;
            RespBean respBean = RespBean.error(RespBeanEnum.BIND_ERROR);
            respBean.setMessage("参数校验异常："+ ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return respBean;
        }
        return RespBean.error(RespBeanEnum.ERROR);
    }
}
