package com.yunhongmin.ratelimiter;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class TokenBucketRateLimiterTest {
    static String defaultKey = "default";

    @Test
    void acquireSucceedsWhenBucketHasTokens() {
        TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(5, 1, RateTimeUnit.SECONDS);
        assertTrue(limiter.tryAcquire(defaultKey));
    }

    @Test
    void acquireFailsWhenBucketIsEmpty() {
        TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(3, 1, RateTimeUnit.SECONDS);
        limiter.tryAcquire(defaultKey);
        limiter.tryAcquire(defaultKey);
        limiter.tryAcquire(defaultKey);
        assertFalse(limiter.tryAcquire(defaultKey));
    }

    @Test
    void bucketStartsFullAndDrains() {
        int bucketSize = 3;
        TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(bucketSize, 1, RateTimeUnit.SECONDS);

        for (int i = 0; i < bucketSize; i++) {
            assertTrue(limiter.tryAcquire(defaultKey), "Expected acquire #" + (i + 1) + " to succeed");
        }
        assertFalse(limiter.tryAcquire(defaultKey), "Expected acquire to fail after bucket is empty");
    }

    @Test
    void bucketRefillsAfterWaiting() throws InterruptedException {
        TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(1, 1, RateTimeUnit.SECONDS);
        assertTrue(limiter.tryAcquire(defaultKey));
        assertFalse(limiter.tryAcquire(defaultKey));

        Thread.sleep(1100); // wait for refill

        assertTrue(limiter.tryAcquire(defaultKey));
    }

    @Test
    void remainderCarriesOverToNextRefillWindow() throws InterruptedException {
        // refillRate=2, SECONDS: adds 2 tokens every 2 seconds
        TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(4, 2, RateTimeUnit.SECONDS);
        // drain bucket
        limiter.tryAcquire(defaultKey);
        limiter.tryAcquire(defaultKey);
        limiter.tryAcquire(defaultKey);
        limiter.tryAcquire(defaultKey);

        // wait 3s: one full 2s period fires (adds 2), 1s remainder carries over
        Thread.sleep(3000);
        assertTrue(limiter.tryAcquire(defaultKey));
        assertTrue(limiter.tryAcquire(defaultKey));
        assertFalse(limiter.tryAcquire(defaultKey));

        // wait 1s more: remainder (1s) + 1s = 2s, triggers another refill
        Thread.sleep(1100);
        assertTrue(limiter.tryAcquire(defaultKey));
        assertTrue(limiter.tryAcquire(defaultKey));
        assertFalse(limiter.tryAcquire(defaultKey));
    }

    @Test
    void concurrentAcquiresNeverExceedBucketSize() throws InterruptedException {
        int bucketSize = 5;
        int threadCount = 50;
        TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(bucketSize, 1, RateTimeUnit.SECONDS);

        AtomicInteger successCount = new AtomicInteger(0);
        CountDownLatch ready = new CountDownLatch(threadCount);
        CountDownLatch start = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                ready.countDown();
                try {
                    start.await(); // all threads start at the same time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                if (limiter.tryAcquire(defaultKey)) {
                    successCount.incrementAndGet();
                }
            });
        }

        ready.await();
        start.countDown();
        executor.shutdown();
        executor.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);

        assertEquals(bucketSize, successCount.get());
    }

    @Test
    void bucketDoesNotExceedMaxSizeOnRefill() throws InterruptedException {
        TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(2, 1, RateTimeUnit.SECONDS);
        // drain bucket
        limiter.tryAcquire(defaultKey);
        limiter.tryAcquire(defaultKey);

        Thread.sleep(3000); // wait long enough to refill more than bucket size

        // should only be able to acquire bucketSize times
        assertTrue(limiter.tryAcquire(defaultKey));
        assertTrue(limiter.tryAcquire(defaultKey));
        assertFalse(limiter.tryAcquire(defaultKey));
    }
}
