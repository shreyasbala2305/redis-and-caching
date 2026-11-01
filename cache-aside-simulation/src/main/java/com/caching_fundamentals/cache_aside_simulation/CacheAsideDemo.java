package com.caching_fundamentals.cache_aside_simulation;

public class CacheAsideDemo {

    public static void main(String[] args) {

        DoctorRepository repository = new DoctorRepository();

        DoctorCacheService cacheService =
                new DoctorCacheService(repository);

        System.out.println("\n===== FIRST REQUEST =====");
        System.out.println("RESPONSE: " + cacheService.getDoctor(42L));

        System.out.println("\n===== SECOND REQUEST =====");
        System.out.println("RESPONSE: " + cacheService.getDoctor(42L));

        // Scenario 1: Update database without invalidating cache.
        System.out.println("\n===== DATABASE UPDATE WITHOUT CACHE INVALIDATION =====");

        repository.updateDoctorFee(42L, 700.0);

        System.out.println("\n===== REQUEST AFTER DATABASE UPDATE =====");
        System.out.println("RESPONSE: " + cacheService.getDoctor(42L));

        // Scenario 2: Manually evict stale cache.
        System.out.println("\n===== MANUAL CACHE INVALIDATION =====");

        cacheService.evictDoctor(42L);

        System.out.println("\n===== REQUEST AFTER CACHE INVALIDATION =====");
        System.out.println("RESPONSE: " + cacheService.getDoctor(42L));

        System.out.println("\n===== NEXT REQUEST =====");
        System.out.println("RESPONSE: " + cacheService.getDoctor(42L));

        // Scenario 3: Correct update through the cache service.
        cacheService.updateDoctorFee(42L, 900.0);

        System.out.println("\n===== REQUEST AFTER SERVICE UPDATE =====");
        System.out.println("RESPONSE: " + cacheService.getDoctor(42L));

        System.out.println("\n===== FINAL REQUEST =====");
        System.out.println("RESPONSE: " + cacheService.getDoctor(42L));

        cacheService.printStatistics();
    }
}