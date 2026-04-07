package com.yunhongmin.cache;

public class RedisCache<V> implements Cache<V> {
    @Override
    public CacheEntry<V> get(String key) throws CacheException {
        // assume that this is implemented
        // V should be serializable
        return null;
    }

    @Override
    public void put(String key, CacheEntry<V> value) throws CacheException {
        // assume that this is implemented
        // V should be deserializable
    }

    @Override
    public void evict(String key) throws CacheException {
        // assume that this is implemented
    }
}
