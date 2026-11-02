package com.caching_fundamentals.cache_aside_simulation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CacheStampedeDemo {

    private static final int THREAD_COUNT = 50;

    private static final Map<Long, Doctor> cache =
            new ConcurrentHashMap<>();

    private static final AtomicInteger databaseCalls =
            new AtomicInteger();

    private static final CountDownLatch startSignal =
            new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor =
                Executors.newFixedThreadPool(THREAD_COUNT);

        System.out.println("===== CACHE STAMPEDE SIMULATION =====");

        for (int i = 0; i < THREAD_COUNT; i++) {

            executor.submit(() -> {

                try {
                    startSignal.await();

                    Doctor doctor = getDoctor(42L);

                    if (doctor != null) {
                        System.out.println(
                                Thread.currentThread().getName()
                                        + " received doctor "
                                        + doctor.getName()
                        );
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        System.out.println(
                "Starting " + THREAD_COUNT + " concurrent requests..."
        );

        startSignal.countDown();

        executor.shutdown();

        while (!executor.isTerminated()) {
            Thread.sleep(100);
        }

        System.out.println("\n===== RESULTS =====");

        System.out.println(
                "Concurrent Requests: " + THREAD_COUNT
        );

        System.out.println(
                "Database Calls: " + databaseCalls.get()
        );
    }

    private static Doctor getDoctor(Long id) {

        Doctor doctor = cache.get(id);

        if (doctor != null) {
            return doctor;
        }

        // Simulated database call.
        databaseCalls.incrementAndGet();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }

        doctor = new Doctor(
                42L,
                "Dr. Rahul Sharma",
                "Cardiology",
                500.0
        );

        cache.put(id, doctor);

        return doctor;
    }
}