package com.lance.demo.microservice.common.annotation;

import com.lance.demo.microservice.common.valid.MobileValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobileValidator.class)
public @interface Mobile {
    String message() default "手机号格式不正确:{}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
