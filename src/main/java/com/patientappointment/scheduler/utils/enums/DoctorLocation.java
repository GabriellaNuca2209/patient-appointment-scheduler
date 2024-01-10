package com.patientappointment.scheduler.utils.enums;

import lombok.Getter;

@Getter
public enum DoctorLocation {

    CLINIC_A("Clinic A"),
    CLINIC_B("Clinic B"),
    CLINIC_C("Clinic C");

    private final String locationLabel;

    DoctorLocation(String locationLabel) {
        this.locationLabel = locationLabel;
    }
}