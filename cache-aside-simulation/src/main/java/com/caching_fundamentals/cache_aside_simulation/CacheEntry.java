package com.caching_fundamentals.cache_aside_simulation;

public class CacheEntry<T> {

    private final T value;
    private final long createdAt;
    private final long ttlNanos;

    public CacheEntry(T value, long ttlMillis) {
        this.value = value;
        this.createdAt = System.nanoTime();
        this.ttlNanos = ttlMillis * 1_000_000L;
    }

    public T getValue() {
        return value;
    }

    public boolean isExpired() {

        long elapsedTime = System.nanoTime() - createdAt;

        return elapsedTime >= ttlNanos;
    }
}