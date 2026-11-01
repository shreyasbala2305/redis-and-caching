package com.caching_fundamentals.cache_aside_simulation;

public class CacheAsideDemo {

    public static void main(String[] args) {

        DoctorRepository repository = new DoctorRepository();

        DoctorCacheService cacheService =
                new DoctorCacheService(repository);

        System.out.println("\n===== CACHE PERFORMANCE TEST =====");

        Long[] requests = {
                42L, 42L, 42L,
                43L, 43L,
                42L, 43L,
                42L, 42L, 43L
        };

        for (int i = 0; i < requests.length; i++) {

            System.out.println("\n===== REQUEST " + (i + 1) + " =====");

            Doctor doctor = cacheService.getDoctor(requests[i]);

            System.out.println("RESPONSE: " + doctor);
        }

        cacheService.printStatistics();
    }
}