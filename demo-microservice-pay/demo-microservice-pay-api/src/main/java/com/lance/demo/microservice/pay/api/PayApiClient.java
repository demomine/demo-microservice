package com.lance.demo.microservice.pay.api;

import com.lance.demo.microservice.pay.common.PayConstants;
import com.lance.demo.microservice.pay.common.model.PayReq;
import com.lance.demo.microservice.pay.common.model.PayRsp;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = PayConstants.APPLICATION_NAME)
public interface PayApiClient {
    @RequestMapping(method = RequestMethod.POST, path = "/pay/pay")
    @LoadBalanced
    PayRsp pay(PayReq payReq);
}
