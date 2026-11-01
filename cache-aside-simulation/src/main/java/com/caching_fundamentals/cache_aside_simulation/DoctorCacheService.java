package com.caching_fundamentals.cache_aside_simulation;

import java.util.HashMap;
import java.util.Map;

public class DoctorCacheService {

    private final DoctorRepository repository;

    private final Map<Long, CacheEntry<Doctor>> cache = new HashMap<>();

    private final long ttlMillis = 10000;

    private int cacheHits = 0;
    private int cacheMisses = 0;

    public DoctorCacheService(DoctorRepository repository) {
        this.repository = repository;
    }

    public Doctor getDoctor(Long id) {

        CacheEntry<Doctor> entry = cache.get(id);

        if (entry != null && !entry.isExpired()) {
            cacheHits++;

            System.out.println("CACHE: HIT for doctor ID " + id);

            return entry.getValue();
        }

        if (entry != null && entry.isExpired()) {
            System.out.println("CACHE: EXPIRED for doctor ID " + id);
            cache.remove(id);
        }

        cacheMisses++;

        System.out.println("CACHE: MISS for doctor ID " + id);

        Doctor doctor = repository.findById(id);

        if (doctor == null) {
            System.out.println("DATABASE: Doctor not found");
            return null;
        }

        cache.put(id, new CacheEntry<>(doctor, ttlMillis));

        System.out.println(
                "CACHE: Stored doctor ID " + id
                        + " with TTL " + ttlMillis + " ms"
        );

        return doctor;
    }

    public void evictDoctor(Long id) {
        if (cache.remove(id) != null) {
            System.out.println(
                    "CACHE: EVICTED doctor ID " + id
            );
        } else {
            System.out.println(
                    "CACHE: No entry to evict for doctor ID " + id
            );
        }
    }

    public void updateDoctorFee(Long id, double newFee) {

        System.out.println("\n===== UPDATE DOCTOR =====");

        repository.updateDoctorFee(id, newFee);

        // Invalidate the old cached value after the database update.
        evictDoctor(id);
    }

    public void printStatistics() {

        int totalLookups = cacheHits + cacheMisses;

        double hitRatio = totalLookups == 0
                ? 0
                : (double) cacheHits / totalLookups * 100;

        System.out.println("\n----- CACHE STATISTICS -----");
        System.out.println("Cache Hits: " + cacheHits);
        System.out.println("Cache Misses: " + cacheMisses);
        System.out.println("Total Lookups: " + totalLookups);
        System.out.printf("Hit Ratio: %.2f%%%n", hitRatio);
        System.out.println("----------------------------");
    }
}