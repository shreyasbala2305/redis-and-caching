package com.caching_fundamentals.cache_aside_simulation;

public class CacheAsideDemo {

    private static final int TOTAL_REQUESTS = 100;
    private static final Long DOCTOR_ID = 42L;

    public static void main(String[] args) {

        DoctorRepository repository = new DoctorRepository();

        System.out.println("\n===== DATABASE VS CACHE BENCHMARK =====");

        // Benchmark 1: Direct database access
        System.out.println("\n===== DIRECT DATABASE ACCESS =====");

        long databaseStart = System.nanoTime();

        for (int i = 0; i < TOTAL_REQUESTS; i++) {
            repository.findById(DOCTOR_ID);
        }

        long databaseEnd = System.nanoTime();

        long databaseTotalNanos = databaseEnd - databaseStart;

        double databaseAverageMs =
                databaseTotalNanos / 1_000_000.0 / TOTAL_REQUESTS;

        System.out.println("Total Requests: " + TOTAL_REQUESTS);
        System.out.printf(
                "Total Time: %.2f ms%n",
                databaseTotalNanos / 1_000_000.0
        );
        System.out.printf(
                "Average Response Time: %.2f ms%n",
                databaseAverageMs
        );

        // Reset database call counter before cache benchmark.
        repository.resetDatabaseCalls();

        // Benchmark 2: Cache-aside access
        System.out.println("\n===== CACHE-ASIDE ACCESS =====");

        DoctorCacheService cacheService =
                new DoctorCacheService(repository);

        long cacheStart = System.nanoTime();

        for (int i = 0; i < TOTAL_REQUESTS; i++) {
            cacheService.getDoctor(DOCTOR_ID);
        }

        long cacheEnd = System.nanoTime();

        long cacheTotalNanos = cacheEnd - cacheStart;

        double cacheAverageMs =
                cacheTotalNanos / 1_000_000.0 / TOTAL_REQUESTS;

        System.out.println("Total Requests: " + TOTAL_REQUESTS);
        System.out.printf(
                "Total Time: %.2f ms%n",
                cacheTotalNanos / 1_000_000.0
        );
        System.out.printf(
                "Average Response Time: %.2f ms%n",
                cacheAverageMs
        );

        // Calculate improvement.
        double improvement =
                ((databaseAverageMs - cacheAverageMs)
                        / databaseAverageMs) * 100;

        System.out.println("\n===== PERFORMANCE COMPARISON =====");

        System.out.printf(
                "Database Average: %.2f ms%n",
                databaseAverageMs
        );

        System.out.printf(
                "Cache Average: %.2f ms%n",
                cacheAverageMs
        );

        System.out.printf(
                "Performance Improvement: %.2f%%%n",
                improvement
        );

        System.out.println(
                "Database Calls During Cache Test: "
                        + repository.getDatabaseCalls()
        );

        cacheService.printStatistics();
    }
}