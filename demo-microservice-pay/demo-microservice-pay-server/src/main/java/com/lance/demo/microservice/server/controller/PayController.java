package com.lance.demo.microservice.server.controller;

import com.lance.demo.microservice.pay.common.model.PayReq;
import com.lance.demo.microservice.pay.common.model.PayRsp;
import com.lance.demo.microservice.server.service.PayService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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

    @GetMapping("/pay")
    public PayRsp pay(HttpServletResponse response) {
        return payService.pay("hello world");
    }
}
