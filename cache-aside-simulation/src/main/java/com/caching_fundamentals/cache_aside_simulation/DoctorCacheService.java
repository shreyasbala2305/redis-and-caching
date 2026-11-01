package com.caching_fundamentals.cache_aside_simulation;

import java.util.HashMap;
import java.util.Map;

public class DoctorCacheService {

    private final Map<Long, CacheEntry<Doctor>> cache =
            new HashMap<>();

    private final DoctorRepository doctorRepository;

    private int cacheHits = 0;
    private int cacheMisses = 0;

    private static final long TTL_MILLIS = 10_000;

    public DoctorCacheService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor getDoctor(Long id) {

        CacheEntry<Doctor> entry = cache.get(id);

        if (entry != null) {

            if (!entry.isExpired()) {

                cacheHits++;

                System.out.println(
                        "CACHE: HIT for doctor ID " + id
                );

                return entry.getValue();
            }

            System.out.println(
                    "CACHE: EXPIRED for doctor ID " + id
            );

            cache.remove(id);
        }

        cacheMisses++;

        System.out.println(
                "CACHE: MISS for doctor ID " + id
        );

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor not found: " + id
                        )
                );

        cache.put(
                id,
                new CacheEntry<>(doctor, TTL_MILLIS)
        );

        System.out.println(
                "CACHE: Stored doctor ID " + id
        );

        return doctor;
    }

    public void updateDoctorFee(Long id, double newFee) {

        System.out.println(
                "\nSERVICE: Updating doctor ID " + id
        );

        // Step 1: Update the database.
        doctorRepository.updateDoctorFee(id, newFee);

        // Step 2: Invalidate the old cache entry.
        evictDoctor(id);
    }

    public void evictDoctor(Long id) {

        CacheEntry<Doctor> removedEntry = cache.remove(id);

        if (removedEntry != null) {

            System.out.println(
                    "CACHE: Evicted doctor ID " + id
            );

        } else {

            System.out.println(
                    "CACHE: No existing entry for doctor ID " + id
            );
        }
    }

    public void displayCacheStatistics() {

        int totalLookups = cacheHits + cacheMisses;

        double hitRatio = totalLookups == 0
                ? 0
                : (cacheHits * 100.0) / totalLookups;

        System.out.println("\n----- CACHE STATISTICS -----");

        System.out.println("Cache Hits: " + cacheHits);
        System.out.println("Cache Misses: " + cacheMisses);
        System.out.println("Total Lookups: " + totalLookups);

        System.out.printf(
                "Hit Ratio: %.2f%%%n",
                hitRatio
        );

        System.out.println("----------------------------");
    }
}