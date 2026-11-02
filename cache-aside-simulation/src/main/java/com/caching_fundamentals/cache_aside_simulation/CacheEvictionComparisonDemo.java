package com.caching_fundamentals.cache_aside_simulation;

import java.util.LinkedHashMap;
import java.util.Map;

public class CacheEvictionComparisonDemo {

    private static final int CAPACITY = 3;

    public static void main(String[] args) {

        System.out.println("===== FIFO CACHE =====");

        Map<Long, Doctor> fifoCache =
                new LinkedHashMap<Long, Doctor>() {

                    @Override
                    protected boolean removeEldestEntry(
                            Map.Entry<Long, Doctor> eldest) {

                        boolean evict = size() > CAPACITY;

                        if (evict) {
                            System.out.println(
                                    "FIFO EVICTED: " + eldest.getKey()
                            );
                        }

                        return evict;
                    }
                };

        insert(fifoCache, 1L);
        insert(fifoCache, 2L);
        insert(fifoCache, 3L);

        fifoCache.get(1L);

        insert(fifoCache, 4L);

        System.out.println("FIFO Final Cache: " + fifoCache.keySet());

        System.out.println("\n===== LRU CACHE =====");

        LinkedHashMap<Long, Doctor> lruCache =
                new LinkedHashMap<Long, Doctor>(
                        CAPACITY, 0.75f, true) {

                    @Override
                    protected boolean removeEldestEntry(
                            Map.Entry<Long, Doctor> eldest) {

                        boolean evict = size() > CAPACITY;

                        if (evict) {
                            System.out.println(
                                    "LRU EVICTED: " + eldest.getKey()
                            );
                        }

                        return evict;
                    }
                };

        insert(lruCache, 1L);
        insert(lruCache, 2L);
        insert(lruCache, 3L);

        lruCache.get(1L);

        insert(lruCache, 4L);

        System.out.println("LRU Final Cache: " + lruCache.keySet());
    }

    private static void insert(
            Map<Long, Doctor> cache,
            Long id) {

        cache.put(
                id,
                new Doctor(
                        id,
                        "Doctor " + id,
                        "General",
                        500.0
                )
        );

        System.out.println("INSERTED: " + id);
    }
}