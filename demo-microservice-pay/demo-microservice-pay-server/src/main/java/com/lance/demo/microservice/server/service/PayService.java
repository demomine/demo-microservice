package com.lance.demo.microservice.server.service;

import com.lance.demo.microservice.pay.common.model.PayReq;
import com.lance.demo.microservice.pay.common.model.PayRsp;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayService {
    public PayRsp pay(PayReq payReq) {
        log.info("req :{}",payReq);
        PayRsp payRsp = new PayRsp();
        payRsp.setCode("0000");
        payRsp.setStatus("SUCCESS");
        return payRsp;
    }

    @HystrixCommand(fallbackMethod = "payFallback")
    public PayRsp pay(String value) {
        if (Math.random() > 0.2) {
            throw new RuntimeException("random exception");
        }
        log.info("=================   request value:{}",value);
        PayRsp payRsp = new PayRsp();
        payRsp.setStatus("success");
        payRsp.setMessage(value);
        return payRsp;
    }


    public PayRsp payFallback(String value) {
        log.error("==============pay fail");
        PayRsp payRsp = new PayRsp();
        payRsp.setStatus("fail");
        payRsp.setMessage(value);
        return payRsp;
    }
}
