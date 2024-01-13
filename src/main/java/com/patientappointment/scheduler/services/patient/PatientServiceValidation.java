package com.patientappointment.scheduler.services.patient;

import com.patientappointment.scheduler.models.dtos.PatientDTO;

public interface PatientServiceValidation {

    void validatePatientAlreadyExists(PatientDTO patientDTO);
}
