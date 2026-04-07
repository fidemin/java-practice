package com.yunhongmin.cache;

public interface DataLoader<V> {
    CacheEntry<V> load(String key);
}
