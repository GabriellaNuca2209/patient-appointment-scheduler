package com.patientappointment.scheduler.utils.enums;

import lombok.Getter;

@Getter
public enum AppointmentStatus {

    SCHEDULED("Scheduled"),
    CANCELED("Canceled"),
    ONGOING("Ongoing"),
    COMPLETED("Completed");

    private final String statusLabel;

    AppointmentStatus(String statusLabel) {
        this.statusLabel = statusLabel;
    }
}