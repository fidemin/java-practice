package com.yunhongmin.cache;

import java.time.Instant;
import java.util.HashMap;

public class MemoryCache<V> implements Cache<V> {
    HashMap<String, CacheEntry<V>> container = new HashMap<>();

    @Override
    synchronized public CacheEntry<V> get(String key) throws CacheException {
        if (!container.containsKey(key)) {
            return null;
        }

        CacheEntry<V> cached = container.get(key);

        if (Instant.now().isAfter(cached.getExpiredAt())) {
            evict(key);
            return null;
        }

        return cached;
    }

    @Override
    synchronized public void put(String key, CacheEntry<V> cacheEntry) throws CacheException{
        container.put(key, cacheEntry);
    }

    @Override
    synchronized public void evict(String key) throws CacheException {
        container.remove(key);
    }
}
