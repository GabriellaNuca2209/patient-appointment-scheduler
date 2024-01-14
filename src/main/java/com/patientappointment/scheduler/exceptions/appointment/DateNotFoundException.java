package com.patientappointment.scheduler.exceptions.appointment;

public class DateNotFoundException extends RuntimeException {

    public DateNotFoundException(String message) {
        super(message);
    }
}
