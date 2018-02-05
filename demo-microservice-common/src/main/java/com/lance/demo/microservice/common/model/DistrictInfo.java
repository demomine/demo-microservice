package com.lance.demo.microservice.common.model;

import lombok.Data;

/**
 * Created by wangyanbo on 17/6/6.
 */
@Data
public class DistrictInfo {
    private String province;
    private String city;
    private String county;

    public DistrictInfo() {

    }

    public DistrictInfo(String province, String city, String county) {
        this.province = province;
        this.city = city;
        this.county = county;
    }
}
