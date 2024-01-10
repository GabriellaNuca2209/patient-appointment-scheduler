package com.patientappointment.scheduler.utils.enums;

import lombok.Getter;

@Getter
public enum DoctorSpecialization {

    CARDIOLOGY("Cardiology"),
    DERMATOLOGY("Dermatology"),
    FAMILY_MEDICINE("Family Medicine"),
    GENERAL_SURGERY("General Surgery"),
    NEUROLOGY("Neurology"),
    GYNECOLOGY("Gynecology"),
    PEDIATRICS("Pediatrics");

    private final String specializationLabel;

    DoctorSpecialization(String specializationLabel) {
        this.specializationLabel = specializationLabel;
    }
}