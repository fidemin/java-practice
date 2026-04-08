package com.yunhongmin.circuitbreaker;

public class CircuitBreakerException extends RuntimeException {
    public CircuitBreakerException(String message) {
        super(message);
    }
}
