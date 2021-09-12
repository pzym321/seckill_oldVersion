package com.pang.seckill.vo;

import com.pang.seckill.utils.ValidatorUtil;
import com.pang.seckill.validator.IsMobile;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Pang ZhengYanMing
 * @Date: 2021/09/11/19:45
 * @Description: 手机号码校验规则
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {
    //是否必填
    private boolean required = false;

    //初始化获取参数注解required的值，ture还是false，传给属性
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            //如果要求必填，就要对填入的value(手机号)进行校验
            return ValidatorUtil.isMobile(value);
        }else{
            //如果手机号码要求非必填，并且value(手机号码)为空，那就直接返回ture。因为没填肯定为空啊
            if(StringUtils.isEmpty(value)){
                return true;
            }else{
                //对填入的value(手机号)进行校验
                return ValidatorUtil.isMobile(value);
            }
        }
    }
}
