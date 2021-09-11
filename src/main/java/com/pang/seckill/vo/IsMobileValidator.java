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
    //获取参数注解required的值，ture还是false，传给属性
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            return ValidatorUtil.isMobile(value);
        }else{
            if(StringUtils.isEmpty(value)){
                return true;
            }else{
                return ValidatorUtil.isMobile(value);
            }
        }
    }
}
