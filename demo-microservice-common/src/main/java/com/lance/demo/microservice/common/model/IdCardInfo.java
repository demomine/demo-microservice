package com.lance.demo.microservice.common.model;

import lombok.Data;


@Data
public class IdCardInfo {
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 县
     */
    private String county;
    /**
     * 生日
     */
    private String birthDate;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 性别
     */
    private boolean isMale;
}
