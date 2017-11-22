package com.lance.demo.microservice.common;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CommonRsp {
    /**
     * 响应状态 SUCCESS FAIL UNKNOWN ..
     */
    private String status;
    /**
     * 响应编码
     */
    private String code;
    /**
     * 响应信息
     */
    private String message;
}
