package com.lance.demo.microservice.common.util;

import com.lance.demo.microservice.common.exception.BaseException;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;
import java.util.Map;

public class BeanUtils {
    public static BeanUtilsBean utilsBean = BeanUtilsBean2.getInstance();

    public static Map<String, String> toMap(Object o) {
        try {
            return utilsBean.describe(o);
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    public static void copy(final Object des, final Object src) {
        assert des != null;
        assert src != null;
        try {
            utilsBean.copyProperties(des,src);
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }
}
