package com.patientappointment.scheduler.utils.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum AppointmentStatus {

    @JsonProperty("Scheduled")
    SCHEDULED("Scheduled"),
    @JsonProperty("Canceled")
    CANCELED("Canceled"),
    @JsonProperty("Ongoing")
    ONGOING("Ongoing"),
    @JsonProperty("Completed")
    COMPLETED("Completed");

    private final String statusLabel;

    AppointmentStatus(String statusLabel) {
        this.statusLabel = statusLabel;
    }
}