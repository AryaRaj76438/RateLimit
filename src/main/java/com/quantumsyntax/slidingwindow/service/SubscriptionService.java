package com.quantumsyntax.slidingwindow.service;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SubscriptionService {
    private final Map<String, UserRequestData> subscriptionCache = new ConcurrentHashMap<>();

    public UserRequestData resolveSubscribedUserData(String key) {
        return subscriptionCache.computeIfAbsent(key, k -> buildUser(resolvePlan(k)));
    }

    private UserRequestData buildUser(SubscriptionPlan plan) {
        return new UserRequestData(plan.getRequestLimit(), plan.getWindowTime());
    }

    private SubscriptionPlan resolvePlan(String key) {
        if (key.startsWith("PS1129-")) return SubscriptionPlan.SUBSCRIPTION_PROFESSIONAL;
        if (key.startsWith("BS1129-")) return SubscriptionPlan.SUBSCRIPTION_BASIC;
        return SubscriptionPlan.SUBSCRIPTION_FREE;
    }
}
