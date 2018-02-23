package com.lance.demo.framework.config.interceptor;

import com.lance.demo.microservice.common.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Aspect
@Configuration
@Order
@Slf4j
public class LogInterceptor {
    private static final String BIZ = "biz";
    private static final String PREFIX = "request message";
    private static final String SUFFIX = "response message";
    @Around("@annotation(bizLog)")
    public Object execute(ProceedingJoinPoint proceedingJoinPoint, Log bizLog) throws Throwable {
        try {
            Object[] args = proceedingJoinPoint.getArgs();
            MDC.put(BIZ, bizLog.biz());
            StringBuilder receive = new StringBuilder();
            if (args != null && args.length > 0) {
                receive.append(PREFIX).append(": ");
                for (int i = 0; i < args.length; i++) {
                    if (contains(bizLog.excludes(), args[i].getClass().getSimpleName())) {
                        continue;
                    }
                    if (bizLog.argMsg().length > i) {
                        String logArg = bizLog.argMsg()[i];
                        receive.append(logArg).append(": ").append("{ ").append(args[i]).append(" }").append(" ");
                    }
                }
            }
            log.info(receive.toString());
            Object proceed = proceedingJoinPoint.proceed(args);
            if (proceed != null) {
                log.info(SUFFIX+": { " + proceed + " }");
            }
            return proceed;
        } finally {
            MDC.remove(BIZ);
        }
    }

    private boolean contains(String[] args, String value) {
        for (String arg : args) {
            if (arg.equals(value)) {
                return true;
            }
        }
        return false;
    }
}