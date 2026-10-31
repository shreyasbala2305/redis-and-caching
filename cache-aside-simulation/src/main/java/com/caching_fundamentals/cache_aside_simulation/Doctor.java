package com.caching_fundamentals.cache_aside_simulation;

public class Doctor {

    private Long id;
    private String name;
    private String specialization;
    private double consultationFee;

    public Doctor(
            Long id,
            String name,
            String specialization,
            double consultationFee
    ) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.consultationFee = consultationFee;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", consultationFee=" + consultationFee +
                '}';
    }
}