package com.yunhongmin.cache;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class CacheEntry<V> {
    V value;
    Instant expiredAt;
}
