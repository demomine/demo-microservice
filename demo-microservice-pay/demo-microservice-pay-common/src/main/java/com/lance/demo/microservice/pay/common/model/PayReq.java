package com.lance.demo.microservice.pay.common.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel("支付请求")
public class PayReq {

    @NotEmpty
    @ApiModelProperty("收款人账号")
    private String payeeNo;

    @Min(0)
    @ApiModelProperty("支付金额")
    private long payAmount;
}
