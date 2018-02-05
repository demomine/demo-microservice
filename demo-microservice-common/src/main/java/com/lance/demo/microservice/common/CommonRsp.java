package com.lance.demo.microservice.common;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@NoArgsConstructor
public class CommonRsp {
    @ApiModelProperty("状态")
    private int status;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("消息")
    private String message;

    public CommonRsp(int status) {
        this.status = status;
    }

    public CommonRsp(String code, String message) {
        this.status = 500;
        this.code = code;
        this.message = message;
    }

    public CommonRsp(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
