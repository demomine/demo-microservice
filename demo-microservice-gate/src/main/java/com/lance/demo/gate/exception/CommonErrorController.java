package com.lance.demo.gate.exception;

import com.lance.demo.gate.GatewayContants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class CommonErrorController implements ErrorController {
    @Value("${error.path:/error}")
    private String errorPath;

    @Override
    public String getErrorPath() {
        return errorPath;
    }

    @RequestMapping(value = "${error.path:/error}", produces = "application/json")
    public ErrorRsp error(HttpServletRequest request, HttpServletResponse response) {

        final int status = getErrorStatus(request);
        final String errorMessage = getErrorMessage(request);
        final String description = getDescription(request);
        final String correlationId = getCorrelationId(response);
        return new  ErrorRsp(status,errorMessage,description,correlationId);
    }

    private String getCorrelationId(HttpServletResponse response) {
        return response.getHeader(GatewayContants.correlationHeader);
    }

    private String getDescription(HttpServletRequest request) {
        return null;
    }

    private int getErrorStatus(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        return statusCode != null ? statusCode : HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    private String getErrorMessage(HttpServletRequest request) {
        final Throwable exc = (Throwable) request.getAttribute("javax.servlet.error.exception");
        return exc != null ? exc.getMessage() : "Unexpected error occurred";
    }


}
