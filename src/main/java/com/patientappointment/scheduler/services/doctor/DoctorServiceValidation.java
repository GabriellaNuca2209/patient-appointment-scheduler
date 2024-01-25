package com.patientappointment.scheduler.services.doctor;

import com.patientappointment.scheduler.models.dtos.DoctorDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public interface DoctorServiceValidation {

    void validateDoctorAlreadyExists(DoctorDTO doctorDTO);
    void validateScheduleBetweenWorkingHours(LocalTime start, LocalTime end);
    void validateScheduleShiftsAreInOrder(LocalTime start, LocalTime end);
    void validateDateIsNotBeforePresent(LocalDate date);
}
