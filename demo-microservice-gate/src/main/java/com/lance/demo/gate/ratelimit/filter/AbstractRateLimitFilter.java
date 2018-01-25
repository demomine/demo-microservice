package com.lance.demo.gate.ratelimit.filter;

import com.lance.demo.gate.ratelimit.RateLimitProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.web.util.UrlPathHelper;
import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractRateLimitFilter extends ZuulFilter {

    public static final String QUOTA_HEADER = "X-RateLimit-Quota-";
    public static final String REMAINING_QUOTA_HEADER = "X-RateLimit-Remaining-Quota-";
    public static final String LIMIT_HEADER = "X-RateLimit-Limit-";
    public static final String REMAINING_HEADER = "X-RateLimit-Remaining-";
    public static final String RESET_HEADER = "X-RateLimit-Reset-";
    public static final String REQUEST_START_TIME = "rateLimitRequestStartTime";

    private final RateLimitProperties properties;
    private final RouteLocator routeLocator;
    private final UrlPathHelper urlPathHelper;

    @Override
    public boolean shouldFilter() {
        return properties.isEnabled() && !policy(route()).isEmpty();
    }

    Route route() {
        String requestURI = urlPathHelper.getPathWithinApplication(RequestContext.getCurrentContext().getRequest());
        return routeLocator.getMatchingRoute(requestURI);
    }

    protected List<RateLimitProperties.Policy> policy(final Route route) {
        if (route != null) {
            return properties.getPolicies(route.getId());
        }
        return properties.getDefaultPolicyList();
    }
}
