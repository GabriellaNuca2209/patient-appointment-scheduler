package com.patientappointment.scheduler.services;

import com.patientappointment.scheduler.models.dtos.PatientDTO;
import com.patientappointment.scheduler.models.entities.Patient;
import com.patientappointment.scheduler.repositories.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientServiceValidation patientServiceValidation;
    private final ModelMapper modelMapper;

    public PatientServiceImpl(PatientRepository patientRepository, PatientServiceValidation patientServiceValidation, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.patientServiceValidation = patientServiceValidation;
        this.modelMapper = modelMapper;
    }

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        patientServiceValidation.validatePatientAlreadyExists(patientDTO);

        patientDTO.setAge(calculateAge(patientDTO.getDob()));
        Patient savedPatient = patientRepository.save(modelMapper.map(patientDTO, Patient.class));
        log.info("Patient " + savedPatient.getFirstName() + " " + savedPatient.getLastName() + " was saved in database.");

        return modelMapper.map(savedPatient, PatientDTO.class);
    }

    private int calculateAge(LocalDate dob) {
        return (int) ChronoUnit.YEARS.between(dob, LocalDate.now());
    }
}
