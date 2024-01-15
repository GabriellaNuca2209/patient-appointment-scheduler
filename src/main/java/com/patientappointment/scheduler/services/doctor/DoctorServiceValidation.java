package com.patientappointment.scheduler.services.doctor;

import com.patientappointment.scheduler.models.dtos.DoctorDTO;

import java.time.LocalTime;

public interface DoctorServiceValidation {

    void validateDoctorAlreadyExists(DoctorDTO doctorDTO);
    void validateScheduleBetweenWorkingHours(LocalTime start, LocalTime end);
}
