package com.lance.demo.microservice.common.valid;

import com.lance.demo.microservice.common.annotation.Mobile;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class MobileValidator implements ConstraintValidator<Mobile, String> {
    private final Pattern PATTERN_MOBILE = Pattern.compile("1\\d{10}");

    @Override
    public void initialize(Mobile mobile) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isNotBlank(s) && !PATTERN_MOBILE.matcher(s).matches()) {
            return false;
        }
        return true;
    }
}
