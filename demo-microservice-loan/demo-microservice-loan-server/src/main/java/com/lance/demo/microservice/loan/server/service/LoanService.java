package com.lance.demo.microservice.loan.server.service;

import com.lance.demo.microservice.loan.common.model.LoanReq;
import com.lance.demo.microservice.loan.common.model.LoanRsp;
import com.lance.demo.microservice.pay.api.PayApiClient;
import com.lance.demo.microservice.pay.common.model.PayReq;
import com.lance.demo.microservice.pay.common.model.PayRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoanService {
    @Autowired
    private PayApiClient payApiClient;
    public LoanRsp pay(LoanReq loanReq) {
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
}
