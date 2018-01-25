package com.lance.demo.gate.ratelimit.filter;

import com.lance.demo.gate.ratelimit.RateLimitKeyGenerator;
import com.lance.demo.gate.ratelimit.RateLimitProperties;
import com.lance.demo.gate.ratelimit.RateLimiter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

/**
 * @author Marcos Barbero
 * @author Liel Chayoun
 */
public class RateLimitPostFilter extends AbstractRateLimitFilter {

    private final RateLimiter rateLimiter;
    private final RateLimitKeyGenerator rateLimitKeyGenerator;

    public RateLimitPostFilter(final RateLimitProperties properties, final RouteLocator routeLocator,
                               final UrlPathHelper urlPathHelper, final RateLimiter rateLimiter,
                               final RateLimitKeyGenerator rateLimitKeyGenerator) {
        super(properties, routeLocator, urlPathHelper);
        this.rateLimiter = rateLimiter;
        this.rateLimitKeyGenerator = rateLimitKeyGenerator;
    }

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_RESPONSE_FILTER_ORDER - 10;
    }

    @Override
    public boolean shouldFilter() {
        return super.shouldFilter() && getRequestStartTime() != null;
    }

    private Long getRequestStartTime() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return (Long) requestAttributes.getAttribute(RateLimitPreFilter.REQUEST_START_TIME, SCOPE_REQUEST);
    }

    @Override
    public Object run() {
        final RequestContext ctx = RequestContext.getCurrentContext();
        final HttpServletRequest request = ctx.getRequest();
        final Route route = route();

        policy(route).forEach(policy -> {
            final Long requestTime = System.currentTimeMillis() - getRequestStartTime();
            final String key = rateLimitKeyGenerator.key(request, route, policy);
            rateLimiter.consume(policy, key, requestTime);
        });

        return null;
    }
}
