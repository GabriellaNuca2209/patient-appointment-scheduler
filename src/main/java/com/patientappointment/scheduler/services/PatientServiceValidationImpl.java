package com.patientappointment.scheduler.services;

import com.patientappointment.scheduler.exceptions.patient.PatientAlreadyExistsException;
import com.patientappointment.scheduler.models.dtos.PatientDTO;
import com.patientappointment.scheduler.models.entities.Patient;
import com.patientappointment.scheduler.repositories.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PatientServiceValidationImpl implements PatientServiceValidation {

    private final PatientRepository patientRepository;

    public PatientServiceValidationImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void validatePatientAlreadyExists(PatientDTO patientDTO) {
        Patient foundPatient = patientRepository.findByEmail(patientDTO.getEmail());

        if (foundPatient != null) throw new PatientAlreadyExistsException("Patient with email " + patientDTO.getEmail() + " already exists");
    }
}
