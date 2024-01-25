package com.patientappointment.scheduler.exceptions.patient;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(String message) {
        super(message);
    }
}
