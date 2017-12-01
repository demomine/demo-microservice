package com.lance.demo.microservice.loan.common.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lance.demo.microservice.common.CommonRsp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ToString(callSuper = true)
@Data
public class LoanRsp extends CommonRsp {

    @ApiModelProperty(name = "姓名")
    private String name;

}
