package com.lance.demo.microservice.common.valid;

import com.lance.demo.microservice.common.annotation.IdCard;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by wangyanbo on 17/6/6.
 */
public class IdCardValidator implements ConstraintValidator<IdCard, String> {
    @Override
    public void initialize(IdCard idCard) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
//        if (StringUtils.isNotBlank(s) && BaseIdCardUtils.parseIdCard(s, true) == null) {
//            return false;
//        }
        return true;
    }
}
