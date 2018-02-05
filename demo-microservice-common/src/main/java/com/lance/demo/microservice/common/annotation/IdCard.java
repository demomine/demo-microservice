package com.lance.demo.microservice.common.annotation;

import com.lance.demo.microservice.common.valid.IdCardValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wangyanbo on 17/2/12.
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdCardValidator.class)
public @interface IdCard {
    String message() default "无效的身份证号:{}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
