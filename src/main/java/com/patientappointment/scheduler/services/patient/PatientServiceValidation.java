package com.patientappointment.scheduler.services.patient;

import com.patientappointment.scheduler.models.dtos.PatientDTO;

import java.time.LocalDate;

public interface PatientServiceValidation {

    void validatePatientAlreadyExists(PatientDTO patientDTO);
    void validatePatientDob(LocalDate dob);
}
