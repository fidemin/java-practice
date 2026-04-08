package com.yunhongmin.ratelimiter;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class SlidingWindowLogRateLimiter implements RateLimiter {
    private final int size;
    private final Duration duration;
    private final Map<String, Queue<Instant>> bucket = new HashMap<>();

    public SlidingWindowLogRateLimiter(int size, Duration duration) {
        this.size = size;
        this.duration = duration;
    }

    @Override
    synchronized public boolean tryAcquire(String key) {
        Queue<Instant> queue = bucket.computeIfAbsent(key, k -> new ArrayDeque<>());

        Instant now = Instant.now();
        while (!queue.isEmpty() && now.minus(duration).isAfter(queue.element())) {
            queue.remove();
        }

        if (queue.size() < size) {
            queue.add(now);
            return true;
        }

        return false;

    }
}
