package com.quantumsyntax.slidingwindow.service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class UserRequestData {
    private final int requestLimit;
    private final int windowTimeInSec;
    private final Queue<Long> requestTimeStamps;

    public UserRequestData(int requestLimit, int windowTimeInSec) {
        this.requestLimit = requestLimit;
        this.windowTimeInSec = windowTimeInSec;
        this.requestTimeStamps = new ConcurrentLinkedDeque<>();
    }

    public boolean isServiceCallAllowed() {
        long currentTime = System.currentTimeMillis() / 1000;
        evictOlder(currentTime);
        if (requestTimeStamps.size() >= requestLimit) return false;
        requestTimeStamps.add(currentTime);
        return true;
    }

    public int getRemainingRequests() {
        return requestLimit - requestTimeStamps.size();
    }

    public int getRequestWaitTime() {
        if (requestTimeStamps.isEmpty()) return 0;
        long currentTime = System.currentTimeMillis() / 1000;
        int elapsed = (int) (currentTime - requestTimeStamps.peek());
        return elapsed > windowTimeInSec ? 0 : windowTimeInSec - elapsed;
    }

    private void evictOlder(long currentTime) {
        while (!requestTimeStamps.isEmpty() && currentTime - requestTimeStamps.peek() > windowTimeInSec) {
            requestTimeStamps.poll();
        }
    }
}
