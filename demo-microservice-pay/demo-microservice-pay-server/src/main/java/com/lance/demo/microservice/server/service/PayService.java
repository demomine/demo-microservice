package com.lance.demo.microservice.server.service;

import com.lance.demo.microservice.pay.common.model.PayReq;
import com.lance.demo.microservice.pay.common.model.PayRsp;
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
}
