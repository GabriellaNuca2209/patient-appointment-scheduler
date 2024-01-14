package com.patientappointment.scheduler.exceptions.appointment;

public class TimeNotFoundException extends RuntimeException {

    public TimeNotFoundException(String message) {
        super(message);
    }
}
