package com.lance.demo.microservice.loan.server.controller;

import com.lance.demo.microservice.loan.common.model.LoanReq;
import com.lance.demo.microservice.loan.common.model.LoanRsp;
import com.lance.demo.microservice.loan.server.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    private LoanService loanService;
    @PostMapping("/pay")
    public LoanRsp pay(@RequestBody @Valid LoanReq loanReq) {
        return loanService.pay(loanReq);
    }
}
