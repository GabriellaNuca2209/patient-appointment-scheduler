package com.patientappointment.scheduler.services.appointment;

import com.patientappointment.scheduler.models.dtos.DoctorDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AppointmentServiceValidator {

    void validateAppointmentDate(LocalDate date, DoctorDTO doctorDTO);
    void validateAppointmentTime(LocalDate date, LocalTime time, DoctorDTO doctorDTO);
}
