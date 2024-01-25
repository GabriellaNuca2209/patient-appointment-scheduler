package com.patientappointment.scheduler.services.appointment;

import com.patientappointment.scheduler.utils.enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AppointmentServiceValidation {

    void validateAppointmentDate(LocalDate date, Long doctorId);
    void validateAppointmentTime(LocalDate date, LocalTime time, Long doctorId);
    void validateAppointmentStatusIsScheduled(AppointmentStatus status);
    void validateAppointmentStatusIsOngoing(AppointmentStatus status);
}
