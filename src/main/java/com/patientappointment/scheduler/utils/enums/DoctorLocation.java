package com.patientappointment.scheduler.utils.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum DoctorLocation {

    @JsonProperty("Clinic A")
    CLINIC_A("Clinic A"),
    @JsonProperty("Clinic B")
    CLINIC_B("Clinic B"),
    @JsonProperty("Clinic C")
    CLINIC_C("Clinic C");

    private final String locationLabel;

    DoctorLocation(String locationLabel) {
        this.locationLabel = locationLabel;
    }
}