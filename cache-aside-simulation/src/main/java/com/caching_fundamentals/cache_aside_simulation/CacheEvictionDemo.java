package com.caching_fundamentals.cache_aside_simulation;

import java.util.LinkedHashMap;
import java.util.Map;

public class CacheEvictionDemo {

    private static final int CACHE_CAPACITY = 3;

    private static final LinkedHashMap<Long, Doctor> cache =
            new LinkedHashMap<>(CACHE_CAPACITY, 0.75f, true) {

                @Override
                protected boolean removeEldestEntry(
                        Map.Entry<Long, Doctor> eldest) {

                    boolean shouldEvict = size() > CACHE_CAPACITY;

                    if (shouldEvict) {
                        System.out.println(
                                "EVICTED doctor ID: " + eldest.getKey()
                        );
                    }

                    return shouldEvict;
                }
            };

    public static void main(String[] args) {

        System.out.println("===== LRU CACHE EVICTION =====");

        putDoctor(1L);
        putDoctor(2L);
        putDoctor(3L);

        printCache();

        System.out.println("\nAccessing doctor ID 1...");
        cache.get(1L);

        System.out.println("\nAdding doctor ID 4...");
        putDoctor(4L);

        printCache();

        System.out.println("\nAccessing doctor ID 3...");
        cache.get(3L);

        System.out.println("\nAdding doctor ID 5...");
        putDoctor(5L);

        printCache();
    }

    private static void putDoctor(Long id) {

        Doctor doctor = new Doctor(
                id,
                "Doctor " + id,
                "General",
                500.0
        );

        cache.put(id, doctor);

        System.out.println("INSERTED doctor ID: " + id);
    }

    private static void printCache() {

        System.out.println("\nCurrent Cache Entries:");

        for (Long id : cache.keySet()) {
            System.out.println("Doctor ID: " + id);
        }
    }
}