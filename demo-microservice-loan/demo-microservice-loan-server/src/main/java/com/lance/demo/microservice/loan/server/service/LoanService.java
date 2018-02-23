package com.lance.demo.microservice.loan.server.service;

import com.lance.demo.microservice.loan.common.model.LoanReq;
import com.lance.demo.microservice.loan.common.model.LoanRsp;
import com.lance.demo.microservice.loan.dao.CreditLoanManager;
import com.lance.demo.microservice.loan.entity.CreditLoanPO;
import com.lance.demo.microservice.pay.api.PayApiClient;
import com.lance.demo.microservice.pay.common.model.PayReq;
import com.lance.demo.microservice.pay.common.model.PayRsp;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@Slf4j
public class LoanService {
    //@Autowired
    private CreditLoanManager creditLoanManager;
    @Autowired
    private PayApiClient payApiClient;
    @HystrixCommand(fallbackMethod = "fail")
    public LoanRsp pay(LoanReq loanReq) {
        if (Math.random() > 0.7) {
            throw new RuntimeException();
        }
        PayReq payReq = new PayReq();
        payReq.setPayAmount(loanReq.getLoanAmount());
        payReq.setPayeeNo(loanReq.getContractNo());
        PayRsp payRsp =  payApiClient.pay(payReq);
        LoanRsp loanRsp = new LoanRsp();
        loanRsp.setCode(payRsp.getCode());
        loanRsp.setMessage(payRsp.getMessage());
        loanRsp.setStatus(payRsp.getStatus());
        return loanRsp;
    }

    public LoanRsp fail(LoanReq loanReq) {
        return new LoanRsp("fail name");
    }

    public LoanRsp getLoanUser(@Valid String  id) {
        CreditLoanPO creditLoanPO = creditLoanManager.selectById(id);
        LoanRsp loanRsp = new LoanRsp();
        loanRsp.setName(creditLoanPO.getContractName());
        return loanRsp;
    }
}
