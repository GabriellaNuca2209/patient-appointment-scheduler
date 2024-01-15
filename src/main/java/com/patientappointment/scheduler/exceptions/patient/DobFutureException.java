package com.patientappointment.scheduler.exceptions.patient;

public class DobFutureException extends RuntimeException {

    public DobFutureException(String message) {
        super(message);
    }
}
