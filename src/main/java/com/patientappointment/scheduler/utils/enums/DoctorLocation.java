package com.patientappointment.scheduler.utils.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum DoctorLocation {

    @JsonProperty("Clinic_A")
    CLINIC_A("Clinic A"),
    @JsonProperty("Clinic_B")
    CLINIC_B("Clinic B"),
    @JsonProperty("Clinic_C")
    CLINIC_C("Clinic C");

    private final String locationLabel;

    DoctorLocation(String locationLabel) {
        this.locationLabel = locationLabel;
    }
}