package com.lance.demo.microservice.pay.api;

import com.lance.demo.microservice.pay.common.PayConstants;
import com.lance.demo.microservice.pay.common.model.PayReq;
import com.lance.demo.microservice.pay.common.model.PayRsp;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient(name = PayConstants.APPLICATION_NAME,url = "http://localhost:9111",path = "/pay")
@FeignClient(name = "${spring.application.name}",url = "http://localhost:9111",path = "/pay")
public interface PayApiClient {
    @RequestMapping(method = RequestMethod.POST, path = "/pay/")
    @LoadBalanced
    PayRsp pay(PayReq payReq);
}
