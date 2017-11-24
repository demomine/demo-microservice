package com.lance.demo.microservice.pay.common.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lance.demo.microservice.common.CommonRsp;
import io.swagger.annotations.ApiModel;
import lombok.ToString;

@ToString(callSuper = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel("支付响应")
public class PayRsp extends CommonRsp {

}
