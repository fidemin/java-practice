package com.yunhongmin.ratelimiter;

import java.time.Duration;
import java.time.LocalTime;

public class TokenBucketRateLimiter implements RateLimiter {
    int bucketSize;
    int refillRate;
    RateTimeUnit rateTimeUnit;
    int currentBucket;
    LocalTime lastRefilled;

    public TokenBucketRateLimiter(int bucketSize, int refillRate, RateTimeUnit refillRateTimeUnit) {
        this.bucketSize = bucketSize;
        this.refillRate = refillRate;
        this.rateTimeUnit = refillRateTimeUnit;
        this.currentBucket = bucketSize;
        this.lastRefilled = LocalTime.now();
    }

    @Override
    public boolean tryAcquire(String key) {
        refill();
        return acquire();
    }

    synchronized private void refill() {
        LocalTime now = LocalTime.now();
        Duration duration = Duration.between(lastRefilled, now);

        long durationSeconds = duration.toSeconds();
        long refillRateSeconds = refillRate * rateTimeUnit.toSeconds();

        if (durationSeconds >= refillRateSeconds) {
            int maxRefill = refillRate * (int) (durationSeconds / refillRateSeconds);
            int remainder = (int) (durationSeconds % refillRateSeconds);
            currentBucket = Math.min(this.bucketSize, this.currentBucket + maxRefill);
            lastRefilled = now.minusSeconds(remainder);
        }
    }

    synchronized private boolean acquire() {
        if (this.currentBucket == 0) {
            return false;
        }
        this.currentBucket--;
        return true;
    }
}
