package com.yunhongmin.circuitbreaker;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class CircuitBreakerImpTest {

    @Test
    void opensAfterFailThresholdReached() throws Exception {
        CircuitBreakerImp cb = new CircuitBreakerImp(3, Duration.ofSeconds(10));

        // fail 3 times to reach threshold
        for (int i = 0; i < 3; i++) {
            assertThrows(RuntimeException.class, () -> cb.execute(() -> { throw new RuntimeException("fail"); }));
        }

        assertEquals(CircuitBreakerStatus.OPEN, cb.getStatus());
        assertThrows(CircuitBreakerException.class, () -> cb.execute(() -> "should not reach"));
    }

    @Test
    void halfOpenAfterTimeoutAndResetsOnSuccess() throws Exception {
        CircuitBreakerImp cb = new CircuitBreakerImp(1, Duration.ofMillis(100));

        assertThrows(RuntimeException.class, () -> cb.execute(() -> { throw new RuntimeException("fail"); }));
        assertEquals(CircuitBreakerStatus.OPEN, cb.getStatus());

        Thread.sleep(150);
        assertEquals(CircuitBreakerStatus.HALF_OPEN, cb.getStatus());

        // successful call in HALF_OPEN resets the circuit breaker
        String result = cb.execute(() -> "ok");
        assertEquals("ok", result);
        assertEquals(CircuitBreakerStatus.CLOSED, cb.getStatus());
    }
}
