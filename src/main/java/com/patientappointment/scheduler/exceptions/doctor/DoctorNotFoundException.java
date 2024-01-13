package com.patientappointment.scheduler.exceptions.doctor;

public class DoctorNotFoundException extends RuntimeException {

    public DoctorNotFoundException(String message) {
        super(message);
    }
}
