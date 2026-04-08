package com.yunhongmin.ratelimiter;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class SlidingWindowLogRateLimiterTest {

    @Test
    void allowsRequestsWithinLimit() {
        SlidingWindowLogRateLimiter limiter = new SlidingWindowLogRateLimiter(3, Duration.ofSeconds(1));

        assertTrue(limiter.tryAcquire("user1"));
        assertTrue(limiter.tryAcquire("user1"));
        assertTrue(limiter.tryAcquire("user1"));
        assertFalse(limiter.tryAcquire("user1"));
    }

    @Test
    void keysAreIsolated() {
        SlidingWindowLogRateLimiter limiter = new SlidingWindowLogRateLimiter(1, Duration.ofSeconds(1));

        assertTrue(limiter.tryAcquire("user1"));
        assertFalse(limiter.tryAcquire("user1"));
        assertTrue(limiter.tryAcquire("user2")); // different key, independent bucket
    }

    @Test
    void allowsRequestsAfterWindowSlides() throws InterruptedException {
        SlidingWindowLogRateLimiter limiter = new SlidingWindowLogRateLimiter(2, Duration.ofSeconds(1));

        assertTrue(limiter.tryAcquire("user1"));
        assertTrue(limiter.tryAcquire("user1"));
        assertFalse(limiter.tryAcquire("user1"));

        Thread.sleep(1100); // wait for window to slide

        assertTrue(limiter.tryAcquire("user1"));
        assertTrue(limiter.tryAcquire("user1"));
        assertFalse(limiter.tryAcquire("user1"));
    }
}
