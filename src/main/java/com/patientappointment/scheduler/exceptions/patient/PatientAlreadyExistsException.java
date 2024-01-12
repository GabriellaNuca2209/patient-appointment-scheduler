package com.patientappointment.scheduler.exceptions.patient;

public class PatientAlreadyExistsException extends RuntimeException {

    public PatientAlreadyExistsException(String message) {
        super(message);
    }
}
