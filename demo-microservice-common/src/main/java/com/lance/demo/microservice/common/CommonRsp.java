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
@AllArgsConstructor
public class CommonRsp {
    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("消息")
    private String message;


    public CommonRsp(String status) {
        this.status = status;
    }


}
