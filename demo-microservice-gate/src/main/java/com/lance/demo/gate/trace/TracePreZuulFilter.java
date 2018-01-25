package com.lance.demo.gate.trace;

import com.lance.demo.gate.GatewayContants;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class TracePreZuulFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return -10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        HttpServletResponse response = RequestContext.getCurrentContext().getResponse();
        String correlationId = request.getHeader(GatewayContants.correlationHeader);
        if (StringUtils.isEmpty(correlationId)) {
            String traceId = UUID.randomUUID().toString();
            response.setHeader(GatewayContants.correlationHeader,traceId );
            MDC.put(GatewayContants.correlationId,traceId);
        }
        return null;
    }
}
