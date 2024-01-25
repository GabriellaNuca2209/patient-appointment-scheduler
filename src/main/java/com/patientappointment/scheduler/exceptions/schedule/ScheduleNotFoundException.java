package com.patientappointment.scheduler.exceptions.schedule;

public class ScheduleNotFoundException extends RuntimeException {

    public ScheduleNotFoundException(String message) {
        super(message);
    }
}
