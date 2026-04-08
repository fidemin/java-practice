package com.yunhongmin.circuitbreaker;

import java.util.concurrent.Callable;

public interface CircuitBreaker {
    <V> V execute(Callable<V> callable) throws Exception;
}
