package com.quantumsyntax.slidingwindow.interceptor;

import com.quantumsyntax.slidingwindow.service.SubscriptionService;
import com.quantumsyntax.slidingwindow.service.UserRequestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimiterInterceptor implements HandlerInterceptor {

    private static final String HEADER_SUBSCRIPTION_KEY = "X-Subscription-Key";
    private static final String HEADER_LIMIT_REMAINING = "X-Rate-Limit-Remaining";
    private static final String HEADER_RETRY_AFTER = "X-Rate-Limit-Retry-After-Seconds";

    @Autowired
    private SubscriptionService subscriptionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String subscriptionKey = request.getHeader(HEADER_SUBSCRIPTION_KEY);
        if (!StringUtils.hasText(subscriptionKey)) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Missing Request Header: " + HEADER_SUBSCRIPTION_KEY);
            return false;
        }

        UserRequestData user = subscriptionService.resolveSubscribedUserData(subscriptionKey);
        if (!user.isServiceCallAllowed()) {
            response.addHeader(HEADER_RETRY_AFTER, String.valueOf(user.getRequestWaitTime()));
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "You've exhausted your quota. Upgrade plan.");
            return false;
        }

        response.addHeader(HEADER_LIMIT_REMAINING, String.valueOf(user.getRemainingRequests()));
        return true;
    }
}
