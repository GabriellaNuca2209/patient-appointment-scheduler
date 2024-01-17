package com.patientappointment.scheduler.exceptions.appointment;

public class AppointmentStatusMismatchException extends RuntimeException {

    public AppointmentStatusMismatchException(String message) {
        super(message);
    }
}
