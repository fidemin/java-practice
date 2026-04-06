package com.yunhongmin.ratelimiter;

public interface RateLimiter {
    public boolean tryAcquire();
}
