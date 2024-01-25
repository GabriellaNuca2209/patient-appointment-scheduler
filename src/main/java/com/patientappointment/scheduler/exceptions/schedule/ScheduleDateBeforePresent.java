package com.patientappointment.scheduler.exceptions.schedule;

public class ScheduleDateBeforePresent extends RuntimeException {

    public ScheduleDateBeforePresent(String message) {
        super(message);
    }
}
