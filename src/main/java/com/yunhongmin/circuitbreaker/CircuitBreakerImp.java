package com.yunhongmin.circuitbreaker;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Callable;

public class CircuitBreakerImp implements CircuitBreaker {
    private final int failThreshold;
    private final Duration timeout;
    private int fails = 0;
    private Instant openedAt = null;

    public CircuitBreakerImp(int failThreshold, Duration timeout) {
        this.failThreshold = failThreshold;
        this.timeout = timeout;
    }

    @Override
    public <V> V execute(Callable<V> callable) throws Exception {
        CircuitBreakerStatus status = getStatus();
        if (status == CircuitBreakerStatus.OPEN) {
            throw new CircuitBreakerException("Circuit breaker is open");
        }

        if (status == CircuitBreakerStatus.CLOSED) {
            try {
                return callable.call();
            } catch (Exception e) {
                handleFail();
                throw e;
            }
        }

        // status == HALF_CLOSED
        try {
            V result = callable.call();
            resetFail();
            return result;
        } catch (Exception e) {
            handleFail();
            throw e;
        }
    }

    synchronized CircuitBreakerStatus getStatus() {
        if (fails >= failThreshold) {
            if (Instant.now().isAfter(openedAt.plus(timeout))) {
                return CircuitBreakerStatus.HALF_OPEN;
            }
            return CircuitBreakerStatus.OPEN;
        }
        return CircuitBreakerStatus.CLOSED;
    }

    synchronized private void resetFail() {
        fails = 0;
        this.openedAt = null;
    }

    synchronized private void handleFail() {
        fails++;
        this.openedAt = Instant.now();
    }
}
