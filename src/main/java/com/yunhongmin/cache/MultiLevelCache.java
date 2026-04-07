package com.yunhongmin.cache;

public class MultiLevelCache<V> implements Cache<V> {
    private final Cache<V> firstCache;
    private final Cache<V> secondCache;
    private final DataLoader<V> dataLoader;
    private final CircuitBreaker circuitBreaker;

    public MultiLevelCache(Cache<V> firstCache, Cache<V> secondCache, DataLoader<V> dataLoader, CircuitBreaker circuitBreaker) {
        this.firstCache = firstCache;
        this.secondCache = secondCache;
        this.dataLoader = dataLoader;
        this.circuitBreaker = circuitBreaker;
    }

    @Override
    public CacheEntry<V> get(String key) throws CacheException {
        CacheEntry<V> cacheEntry = null;
        try {
            cacheEntry = firstCache.get(key);
        } catch (CacheException e) {
            // warning log
        }

        if (cacheEntry != null) {
            return cacheEntry;
        }

        try {
            cacheEntry = secondCache.get(key);
            circuitBreaker.recordSuccess();
        } catch (CacheException e) {
            circuitBreaker.recordFailure();
            if (circuitBreaker.isOpen()) {
                throw e;
            }
        }

        if (cacheEntry != null) {
            firstCache.put(key, cacheEntry);
            return cacheEntry;
        }

        cacheEntry = loadOriginalData(key);

        if (cacheEntry != null) {
            if (!circuitBreaker.isOpen()) {
                put(key, cacheEntry);
            } else {
                firstCache.put(key, cacheEntry);
            }
            return cacheEntry;
        }

        return null;
    }

    @Override
    public void put(String key, CacheEntry<V> entry) throws CacheException {
        secondCache.put(key, entry);

        try {
            firstCache.put(key, entry);
        } catch (CacheException e1) {
            firstCache.evict(key);
            // warning log 추가
        }
    }

    @Override
    public void evict(String key) throws CacheException {
        firstCache.evict(key);
        secondCache.evict(key);
    }

    private CacheEntry<V> loadOriginalData(String key) {
        try {
            return dataLoader.load(key);
        } catch (Exception exception) {
            throw new CacheException(exception.toString());
        }
    }
}
