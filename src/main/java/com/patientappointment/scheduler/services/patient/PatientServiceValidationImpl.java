package com.patientappointment.scheduler.services.patient;

import com.patientappointment.scheduler.exceptions.patient.DobFutureException;
import com.patientappointment.scheduler.exceptions.patient.PatientAlreadyExistsException;
import com.patientappointment.scheduler.exceptions.patient.UnderageException;
import com.patientappointment.scheduler.models.dtos.PatientDTO;
import com.patientappointment.scheduler.models.entities.Patient;
import com.patientappointment.scheduler.repositories.PatientRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.patientappointment.scheduler.utils.constants.DTOConstants.MINIMUM_AGE;

@Component
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

    @Override
    public void validatePatientDob(LocalDate dob) {
        int age = (int) ChronoUnit.YEARS.between(dob, LocalDate.now());
        if (age < MINIMUM_AGE) throw new UnderageException("Underage patient");

        if (dob.isAfter(LocalDate.now())) throw new DobFutureException("Date: " + dob + " is invalid.");
    }
}
