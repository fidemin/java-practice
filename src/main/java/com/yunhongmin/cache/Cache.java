package com.yunhongmin.cache;

public interface Cache<V> {
    CacheEntry<V> get(String key) throws CacheException;
    void put(String key, CacheEntry<V> value) throws CacheException;
    void evict(String key) throws CacheException;
}
