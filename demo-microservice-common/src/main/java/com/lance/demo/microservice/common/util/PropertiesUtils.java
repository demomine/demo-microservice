package com.lance.demo.microservice.common.util;

import com.lance.demo.microservice.common.constants.Symbol;
import com.lance.demo.microservice.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

@Slf4j
public class PropertiesUtils {

    public static Properties load(String fileName) {
        fileName = fileName.startsWith(Symbol.BACK_SPLASH) ? fileName.substring(1) : fileName;
        InputStream stream = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
        Properties properties = new Properties();
        try {
            properties.load(stream);
            return properties;
        } catch (IOException e) {
            throw new BaseException(e);
        }
    }
}
