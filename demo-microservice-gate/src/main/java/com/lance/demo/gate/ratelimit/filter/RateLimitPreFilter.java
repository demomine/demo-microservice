package com.lance.demo.gate.ratelimit.filter;

import com.lance.demo.gate.ratelimit.Rate;
import com.lance.demo.gate.ratelimit.RateLimitKeyGenerator;
import com.lance.demo.gate.ratelimit.RateLimitProperties;
import com.lance.demo.gate.ratelimit.RateLimiter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.FORM_BODY_WRAPPER_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;


public class RateLimitPreFilter extends AbstractRateLimitFilter {

    private final RateLimiter rateLimiter;
    private final RateLimitKeyGenerator rateLimitKeyGenerator;

    public RateLimitPreFilter(final RateLimitProperties properties, final RouteLocator routeLocator,
                              final UrlPathHelper urlPathHelper, final RateLimiter rateLimiter,
                              final RateLimitKeyGenerator rateLimitKeyGenerator) {
        super(properties, routeLocator, urlPathHelper);
        this.rateLimiter = rateLimiter;
        this.rateLimitKeyGenerator = rateLimitKeyGenerator;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FORM_BODY_WRAPPER_FILTER_ORDER;
    }

    @Override
    public Object run() {
        final RequestContext ctx = RequestContext.getCurrentContext();
        final HttpServletResponse response = ctx.getResponse();
        final HttpServletRequest request = ctx.getRequest();
        final Route route = route();

        policy(route).forEach(policy -> {
            final String key = rateLimitKeyGenerator.key(request, route, policy);
            final Rate rate = rateLimiter.consume(policy, key, null);
            final String httpHeaderKey = key.replaceAll("[^A-Za-z0-9-.]", "_").replaceAll("__", "_");

            final Long limit = policy.getLimit();
            final Long remaining = rate.getRemaining();
            if (limit != null) {
                response.setHeader(LIMIT_HEADER + httpHeaderKey, String.valueOf(limit));
                response.setHeader(REMAINING_HEADER + httpHeaderKey, String.valueOf(Math.max(remaining, 0)));
            }

            final Long quota = policy.getQuota();
            final Long remainingQuota = rate.getRemainingQuota();
            if (quota != null) {
                RequestContextHolder.getRequestAttributes()
                    .setAttribute(REQUEST_START_TIME, System.currentTimeMillis(), SCOPE_REQUEST);
                response.setHeader(QUOTA_HEADER + httpHeaderKey, String.valueOf(quota));
                response.setHeader(REMAINING_QUOTA_HEADER + httpHeaderKey,
                    String.valueOf(MILLISECONDS.toSeconds(Math.max(remainingQuota, 0))));
            }

            response.setHeader(RESET_HEADER + httpHeaderKey, String.valueOf(rate.getReset()));

            if ((limit != null && remaining < 0) || (quota != null && remainingQuota < 0)) {
                HttpStatus tooManyRequests = HttpStatus.TOO_MANY_REQUESTS;
                ctx.setResponseStatusCode(tooManyRequests.value());
                ctx.put("rateLimitExceeded", "true");
                ctx.setSendZuulResponse(false);
                ZuulException zuulException =
                    new ZuulException(tooManyRequests.toString(), tooManyRequests.value(), null);
                throw new ZuulRuntimeException(zuulException);
            }
        });

        return null;
    }
}
