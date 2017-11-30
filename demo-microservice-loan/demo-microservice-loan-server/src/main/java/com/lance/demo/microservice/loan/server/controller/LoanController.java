package com.lance.demo.microservice.loan.server.controller;

import com.lance.demo.microservice.common.CommonRsp;
import com.lance.demo.microservice.loan.common.model.LoanReq;
import com.lance.demo.microservice.loan.common.model.LoanRsp;
// import com.lance.demo.microservice.loan.server.config.BusEvent;
import com.lance.demo.microservice.loan.server.config.LoanProperties;
import com.lance.demo.microservice.loan.server.service.LoanService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
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

    //@Value("${loan.status}")
    private volatile String status;

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

    @GetMapping("/bus_value")
    public CommonRsp busValue() {
        return new CommonRsp(status);
    }

   /* @EventListener
    public void event(BusEvent busEvent) {
        status = busEvent.getValues().get("loan.status");
    }
*/
    @GetMapping("/user/{id}")
    LoanRsp getLoanUser(@PathVariable @Valid String  id) {
        return loanService.getLoanUser(id);
    }
}
