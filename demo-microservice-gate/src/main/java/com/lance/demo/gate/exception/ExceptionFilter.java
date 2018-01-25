package com.lance.demo.gate.exception;

import com.lance.demo.framework.exception.ServerException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.util.ReflectionUtils;

@Slf4j
public class ExceptionFilter extends ZuulFilter {
    @Value("${error.path:/error}")
    private String errorPath;
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER -1;
    }

    @Override
    public Object run() {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            Throwable throwable = ctx.getThrowable();
            if (!ctx.getResponse().isCommitted()) {
                String message = throwable.getCause().getMessage();
                int status = ctx.getResponseStatusCode();
                String description = "";
                if (throwable instanceof ServerException) {
                    description = ((ServerException) throwable).getDescription();
                }
                String stringBuilder = "{" + "\"status\": " + status + "," +
                        "\"message\": \"" + message + "\"," +
                        "\"description\": \"" + description +
                        "\"}";
                log.info("string:-----:  {}",stringBuilder);
                ctx.setResponseBody(stringBuilder);
            }
        } catch (Exception var5) {
            ReflectionUtils.rethrowRuntimeException(var5);
        }

        return null;
    }

    ZuulException findZuulException(Throwable throwable) {
        if (throwable.getCause() instanceof ZuulRuntimeException) {
            return (ZuulException)throwable.getCause().getCause();
        } else if (throwable.getCause() instanceof ZuulException) {
            return (ZuulException)throwable.getCause();
        } else {
            return throwable instanceof ZuulException ? (ZuulException)throwable : new ZuulException(throwable, 500, (String)null);
        }
    }

}
