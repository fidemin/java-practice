package com.yunhongmin.cache;

public interface CircuitBreaker {
    boolean isOpen();
    void recordFailure();
    void recordSuccess();
}
