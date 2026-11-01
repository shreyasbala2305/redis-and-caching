package com.caching_fundamentals.cache_aside_simulation;

import java.util.HashMap;
import java.util.Map;

public class DoctorRepository {

    private final Map<Long, Doctor> database = new HashMap<>();

    public DoctorRepository() {
        database.put(42L, new Doctor(
                42L, "Dr. Rahul Sharma", "Cardiology", 500.0
        ));

        database.put(43L, new Doctor(
                43L, "Dr. Priya Verma", "Neurology", 700.0
        ));
    }

    public Doctor findById(Long id) {
        System.out.println("DATABASE: Fetching doctor ID " + id);
        return database.get(id);
    }

    public void updateDoctorFee(Long id, double newFee) {
        Doctor existing = database.get(id);

        if (existing == null) {
            throw new IllegalArgumentException(
                    "Doctor not found with ID: " + id
            );
        }

        Doctor updatedDoctor = new Doctor(
                existing.getId(),
                existing.getName(),
                existing.getSpecialization(),
                newFee
        );

        database.put(id, updatedDoctor);

        System.out.println(
                "DATABASE: Updated doctor ID " + id
                        + " fee to " + newFee
        );
    }
}