package com.lance.demo.microservice.loan.common.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lance.demo.microservice.common.CommonRsp;
import lombok.ToString;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ToString(callSuper = true)
public class LoanRsp extends CommonRsp {

}
