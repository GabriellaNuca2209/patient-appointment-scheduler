package com.patientappointment.scheduler.exceptions.patient;

public class UnderageException extends RuntimeException {

    public UnderageException(String message) {
        super(message);
    }
}
