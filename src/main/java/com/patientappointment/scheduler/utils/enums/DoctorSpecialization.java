package com.patientappointment.scheduler.utils.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum DoctorSpecialization {

    @JsonProperty("Cardiology")
    CARDIOLOGY("Cardiology"),
    @JsonProperty("Dermatology")
    DERMATOLOGY("Dermatology"),
    @JsonProperty("Family Medicine")
    FAMILY_MEDICINE("Family Medicine"),
    @JsonProperty("General Surgery")
    GENERAL_SURGERY("General Surgery"),
    @JsonProperty("Neurology")
    NEUROLOGY("Neurology"),
    @JsonProperty("Gynecology")
    GYNECOLOGY("Gynecology");

    private final String specializationLabel;

    DoctorSpecialization(String specializationLabel) {
        this.specializationLabel = specializationLabel;
    }
}