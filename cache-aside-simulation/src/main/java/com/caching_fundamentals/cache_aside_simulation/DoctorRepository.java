package com.caching_fundamentals.cache_aside_simulation;

import java.util.HashMap;
import java.util.Map;

public class DoctorRepository {

    private final Map<Long, Doctor> database = new HashMap<>();

    private int databaseCalls = 0;

    public DoctorRepository() {
        database.put(42L, new Doctor(
                42L, "Dr. Rahul Sharma", "Cardiology", 500.0
        ));

        database.put(43L, new Doctor(
                43L, "Dr. Priya Verma", "Neurology", 700.0
        ));
    }

    public Doctor findById(Long id) {

        databaseCalls++;

        simulateDatabaseLatency();

        return database.get(id);
    }

    private void simulateDatabaseLatency() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(
                    "Database simulation interrupted", e
            );
        }
    }

    public int getDatabaseCalls() {
        return databaseCalls;
    }

    public void resetDatabaseCalls() {
        databaseCalls = 0;
    }
}