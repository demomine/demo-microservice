package com.lance.demo.microservice.server.controller;

import com.lance.demo.microservice.pay.common.model.PayReq;
import com.lance.demo.microservice.pay.common.model.PayRsp;
import com.lance.demo.microservice.server.service.PayService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/pay")
@Api("pay controller")
public class PayController {
    @Autowired
    private PayService payService;

    @PostMapping("/pay")
    public PayRsp pay(@RequestBody @Valid PayReq payReq) {
        return payService.pay(payReq);
    }
}
