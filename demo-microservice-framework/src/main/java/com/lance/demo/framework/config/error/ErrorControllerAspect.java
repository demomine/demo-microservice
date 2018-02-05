package com.lance.demo.framework.config.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.lance.demo.microservice.common.exception.BaseException;
import com.lance.demo.microservice.common.model.ErrorInfo;
import com.lance.demo.microservice.common.util.JsonUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.AspectException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.rmi.ServerException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

@Slf4j
@RestControllerAdvice
@NoArgsConstructor
public class ErrorControllerAspect {
    /*private final ObjectMapper objectMapper = JsonUtils.newInstance();
    @Autowired
    private BaseErrorContentService baseErrorContentService;

    @PostConstruct
    public void init() {
//        objectMapper.configure(Fe)
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public void validationError(MethodArgumentNotValidException e, HttpServletResponse resp) {
        StringBuilder buf = new StringBuilder();
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        IntStream.range(0, errors.size()).forEach((index) -> {
            if (index != 0) {
                buf.append(",");
            }

            FieldError error = (FieldError)errors.get(index);
            String message = error.getDefaultMessage();
            if (StringUtils.isNotBlank(message) && error.getRejectedValue() != null) {
                message = message.replace("{}", error.getRejectedValue().toString());
            }

            buf.append(error.getField()).append(":").append(message);
        });
        log.error("异常:{}", buf.toString());
        MDC.put("statusCode", "400");
        this.writeErrorResponse(resp, this.errorContentByKey("method.argument.not.valid", buf.toString()));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public void constraintViolation(ConstraintViolationException e, HttpServletResponse resp) {
        StringBuilder buf = new StringBuilder();
        ConstraintViolation[] constraints = (ConstraintViolation[])e.getConstraintViolations().toArray(new ConstraintViolation[0]);
        IntStream.range(0, constraints.length).forEach((index) -> {
            if (index != 0) {
                buf.append(",");
            }

            String message = constraints[index].getMessage();
            if (StringUtils.isNotBlank(message) && constraints[index].getInvalidValue() != null) {
                message = message.replace("{}", constraints[index].getInvalidValue().toString());
            }

            buf.append(message);
        });
        log.error("异常:{}", buf.toString());
        MDC.put("statusCode", "400");
        this.writeErrorResponse(resp, this.errorContentByKey("method.argument.not.valid", buf.toString()));
    }

    @ExceptionHandler({BindException.class})
    public void bindException(BindException e, HttpServletResponse resp) {
        StringBuilder msg = new StringBuilder();
        IntStream.range(0, e.getFieldErrorCount()).forEach((index) -> {
            if (index != 0) {
                msg.append(",");
            }

            FieldError error = (FieldError)e.getFieldErrors().get(index);
            msg.append(error.getField()).append(":").append(error.getDefaultMessage());
        });
        log.error("异常:{}", msg);
        MDC.put("statusCode", "400");
        this.writeErrorResponse(resp, this.errorContentByKey("method.argument.not.valid", msg.toString()));
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public void missingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletResponse resp) {
        ErrorInfo<String> errorContent = this.errorContentByKey("missing.servlet.request.parameter", e.getParameterName());
        log.error("异常:{}", errorContent.getMsg());
        MDC.put("statusCode", "400");
        this.writeErrorResponse(resp, errorContent);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, TypeMismatchException.class})
    public void messageException(Exception e, HttpServletResponse resp) {
        ErrorContent errorContent = this.errorContentByKey("bad.request");
        if (e.getCause() != null && e.getCause() instanceof UnrecognizedPropertyException) {
            String propName = ((UnrecognizedPropertyException)e.getCause()).getPropertyName();
            log.error("无法识别的属性[{}]", propName);
            errorContent = this.errorContentByKey("param.unrecognized", propName);
        } else if (e.getCause() != null && e.getCause() instanceof InvalidFormatException) {
            InvalidFormatException cause = (InvalidFormatException)e.getCause();
            String value = cause.getValue().toString();
            String targetCls = cause.getTargetType().getSimpleName();
            log.error("值[{}]不是有效的[{}]类型", value, targetCls);
            errorContent = this.errorContentByKey("param.invalid.format", value, targetCls);
        } else {
            log.error(ExceptionUtils.getStackTrace(e));
        }

        MDC.put("statusCode", "400");
        this.writeErrorResponse(resp, errorContent);
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class, HttpMediaTypeNotAcceptableException.class})
    public void badRequest(Exception e, HttpServletResponse resp) {
        log.error(ExceptionUtils.getStackTrace(e));
        MDC.put("statusCode", "400");
        this.writeErrorResponse(resp, this.errorContentByKey("media.type.not.supported"));
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public void illegalArgument(IllegalArgumentException e, HttpServletResponse resp) {
        log.error(ExceptionUtils.getStackTrace(e));
        this.writeErrorResponse(resp, this.errorContentByKey("illegal.argument", e.getMessage()));
    }

    @ExceptionHandler({SocketException.class})
    public void socketException(SocketException e, HttpServletResponse resp) {
        log.error(ExceptionUtils.getStackTrace(e));
        this.writeErrorResponse(resp, this.errorContentByKey("socket.exception"));
    }

    @ExceptionHandler({TimeoutException.class, SocketTimeoutException.class, BaseTimeoutException.class})
    public void socketTimeoutException(Exception e, HttpServletResponse resp) {
        log.warn("请求超时");
        this.writeErrorResponse(resp, this.errorContentByKey("socket.timeout"));
    }

    @ExceptionHandler({IpInvalidException.class})
    public void ipInvalidException(IpInvalidException e, HttpServletResponse resp) {
        this.writeErrorResponse(resp, this.errorContentByKey("ip.invalid", e.getIp()));
    }

    @ExceptionHandler({ServerException.class})
    public void serverException(ServerException e, HttpServletResponse resp) {
        ErrorContent errorContent = this.errorContentByKey(e.getErrorKey(), e.getArgs());
        if (e.isLogError()) {
            log.error("异常:{}", errorContent.getMsg());
        } else {
            log.warn(errorContent.getMsg());
        }

        this.writeErrorResponse(resp, errorContent);
    }

    @ExceptionHandler({AspectException.class})
    public void aspectException(AspectException e, HttpServletResponse resp) {
        ErrorContent errorContent;
        if (StringUtils.isNotBlank(e.getErrorKey())) {
            errorContent = this.errorContentByKey(e.getErrorKey(), e.getArgs());
        } else {
            errorContent = this.errorContentByKey("internal.server.error", e.getMessage());
        }

        if (e.isLogError()) {
            log.error("异常:{}", errorContent.getMsg());
        } else {
            log.warn("异常:{}", errorContent.getMsg());
        }

        this.writeErrorResponse(resp, errorContent);
    }

    @ExceptionHandler({RetrofitException.class})
    public void retrofitException(RetrofitException e, HttpServletResponse resp) {
        this.writeErrorResponse(resp, this.errorContentByKey("third.party.exception", e.getMessage()));
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public void noHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {
        log.warn("[{}]请求了不存在的接口:{}", BaseIpUtils.getIp(request), e.getRequestURL());
    }

    @ExceptionHandler({Throwable.class})
    public void unknownError(Throwable e, HttpServletResponse resp) {
        log.error("发生未知的异常", e);
        this.writeErrorResponse(resp, this.errorContentByKey("internal.server.error", e.getMessage()));
    }

    private void writeErrorResponse(HttpServletResponse res, ErrorContent errorContent) {
        try {
            res.setCharacterEncoding(StandardCharsets.UTF_8.name());
            res.setContentType("application/json");
            res.setStatus(errorContent.getHttpCode());
            this.objectMapper.writeValue(res.getOutputStream(), errorContent.exceptionResp());
        } catch (IOException var4) {
            log.error("Can not write to the response output stream: {}", var4);
        }

    }

    private ErrorInfo<String> errorContentByKey(String key, String... args) {
        return this.baseErrorContentService.errorContentByKey(key, args);
    }*/
}
