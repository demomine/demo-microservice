package com.lance.demo.microservice.common.valid;

import com.lance.demo.microservice.common.annotation.Ip;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Created by wangyanbo on 17/6/6.
 */
public class IpValidator implements ConstraintValidator<Ip, String> {
    private final Pattern PATTERN_IP = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");

    @Override
    public void initialize(Ip ip) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isNotBlank(s) && !PATTERN_IP.matcher(s).matches()) {
            return false;
        }
        return true;
    }
}
