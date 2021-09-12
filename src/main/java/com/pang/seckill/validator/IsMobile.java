package com.pang.seckill.validator;

import com.pang.seckill.vo.IsMobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Pang ZhengYanMing
 * @Date: 2021/09/11/19:42
 * @Description: 验证手机号自定义注解
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Constraint:自定义校验规则
@Constraint(
        validatedBy = { IsMobileValidator.class}
)
public @interface IsMobile {

    /**
     * 要求手机号码是否为必填，默认为ture
     * @return
     */
    boolean required() default true;

    /**
     * 报错信息
     * @return
     */
    String message() default "手机号码格式错误";

    Class<?>[] groups() default {};

    //没有payload()会报错
    Class<? extends Payload>[] payload() default {};

}
