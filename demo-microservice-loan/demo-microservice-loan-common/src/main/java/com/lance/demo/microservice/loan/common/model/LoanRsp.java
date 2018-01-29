package com.lance.demo.microservice.loan.common.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lance.demo.microservice.common.CommonRsp;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ToString(callSuper = true)
@Getter@Setter@NoArgsConstructor
public class LoanRsp extends CommonRsp {

    @ApiModelProperty(name = "姓名")
    private String name;

    public LoanRsp(String name) {
        this.name = name;
    }
}
