package com.caching_fundamentals.cache_aside_simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DoctorRepository {

    private final Map<Long, Doctor> database = new HashMap<>();

    public DoctorRepository() {

        database.put(
                42L,
                new Doctor(
                        42L,
                        "Dr. Rahul Sharma",
                        "Cardiology",
                        500.0
                )
        );

        database.put(
                43L,
                new Doctor(
                        43L,
                        "Dr. Priya Deshmukh",
                        "Dermatology",
                        700.0
                )
        );
    }

    public Optional<Doctor> findById(Long id) {

        System.out.println(
                "DATABASE: Fetching doctor ID " + id
        );

        return Optional.ofNullable(database.get(id));
    }

    public void updateDoctorFee(Long id, double newFee) {

        Doctor doctor = database.get(id);

        if (doctor == null) {
            throw new RuntimeException(
                    "Doctor not found: " + id
            );
        }

        doctor.setConsultationFee(newFee);

        System.out.println(
                "DATABASE: Updated consultation fee to ₹" + newFee
        );
    }
}