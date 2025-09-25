package com.quantumsyntax.slidingwindow.service;

public enum SubscriptionPlan {
    SUBSCRIPTION_FREE(2, 30),
    SUBSCRIPTION_BASIC(10, 30),
    SUBSCRIPTION_PROFESSIONAL(20, 30);

    private final int requestLimit;
    private final int windowTime;

    SubscriptionPlan(int requestLimit, int windowTime) {
        this.requestLimit = requestLimit;
        this.windowTime = windowTime;
    }

    public int getRequestLimit() { return requestLimit; }
    public int getWindowTime() { return windowTime; }
}
