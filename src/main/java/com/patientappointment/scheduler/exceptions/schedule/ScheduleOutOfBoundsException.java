package com.patientappointment.scheduler.exceptions.schedule;

public class ScheduleOutOfBoundsException extends RuntimeException {

    public ScheduleOutOfBoundsException(String message) {
        super(message);
    }
}
