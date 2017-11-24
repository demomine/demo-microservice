package com.lance.demo.microservice.loan.server.controller;

import com.lance.demo.microservice.common.CommonRsp;
import com.lance.demo.microservice.loan.common.model.LoanReq;
import com.lance.demo.microservice.loan.common.model.LoanRsp;
import com.lance.demo.microservice.loan.server.config.LoanProperties;
import com.lance.demo.microservice.loan.server.service.LoanService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/loan")
@Api("loan controller")
public class LoanController {
    @Autowired
    private LoanService loanService;
    @Autowired
    private Environment environment;
    @Autowired
    private LoanProperties loanProperties;

    @Value("${loan.status}")
    private String status;

    @PostMapping("/pay")
    public LoanRsp pay(@RequestBody @Valid LoanReq loanReq) {
        return loanService.pay(loanReq);
    }

    @GetMapping("/bus")
    public CommonRsp bus() {
        String status = environment.getProperty("loan.status");
        return new CommonRsp(status);
    }

    @GetMapping("/bus_props")
    public CommonRsp busProps() {
        return new CommonRsp(loanProperties.getStatus());
    }

    /**
     * 不生效
     * @return
     */
    @GetMapping("/bus_value")
    public CommonRsp busValue() {
        return new CommonRsp(status);
    }
}
