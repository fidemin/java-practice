package com.yunhongmin.ratelimiter;

public interface RateLimiter {
    boolean tryAcquire(String key);
}
