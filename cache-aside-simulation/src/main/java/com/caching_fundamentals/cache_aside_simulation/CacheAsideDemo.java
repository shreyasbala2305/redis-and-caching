package com.caching_fundamentals.cache_aside_simulation;

public class CacheAsideDemo {

    public static void main(String[] args) {

        DoctorRepository repository = new DoctorRepository();

        DoctorCacheService cacheService =
                new DoctorCacheService(repository);

        System.out.println("\n===== FIRST REQUEST =====");

        Doctor doctor1 = cacheService.getDoctor(42L);

        System.out.println("RESPONSE: " + doctor1);


        System.out.println("\n===== SECOND REQUEST =====");

        Doctor doctor2 = cacheService.getDoctor(42L);

        System.out.println("RESPONSE: " + doctor2);


        System.out.println("\n===== THIRD REQUEST =====");

        Doctor doctor3 = cacheService.getDoctor(43L);

        System.out.println("RESPONSE: " + doctor3);


        System.out.println("\n===== FOURTH REQUEST =====");

        Doctor doctor4 = cacheService.getDoctor(43L);

        System.out.println("RESPONSE: " + doctor4);


        cacheService.displayCacheStatistics();
    }
}