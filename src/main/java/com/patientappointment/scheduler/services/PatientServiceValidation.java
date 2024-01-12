package com.patientappointment.scheduler.services;

import com.patientappointment.scheduler.models.dtos.PatientDTO;

public interface PatientServiceValidation {

    void validatePatientAlreadyExists(PatientDTO patientDTO);
}
