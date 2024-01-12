package com.patientappointment.scheduler.services;

import com.patientappointment.scheduler.models.dtos.PatientDTO;

public interface PatientService {

    PatientDTO createPatient(PatientDTO patientDTO);
}
