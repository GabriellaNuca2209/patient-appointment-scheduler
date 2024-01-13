package com.patientappointment.scheduler.services.patient;

import com.patientappointment.scheduler.exceptions.patient.PatientNotFoundException;
import com.patientappointment.scheduler.models.dtos.PatientDTO;
import com.patientappointment.scheduler.models.entities.Patient;
import com.patientappointment.scheduler.repositories.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

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

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream().map(patient -> modelMapper.map(patient, PatientDTO.class)).toList();
    }

    @Override
    public PatientDTO getPatient(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient with id " + id + " not found"));

        return modelMapper.map(patient, PatientDTO.class);
    }

    @Override
    public PatientDTO updatePatient(Long id, PatientDTO patientDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient with id " + id + " not found"));

        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setEmail(patientDTO.getEmail());

        Patient savedPatient = patientRepository.save(patient);
        log.info("Patient with id " + id + " was updated in db.");

        return modelMapper.map(savedPatient, PatientDTO.class);
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient with id " + id + " not found"));
        patientRepository.delete(patient);
    }

    private int calculateAge(LocalDate dob) {
        return (int) ChronoUnit.YEARS.between(dob, LocalDate.now());
    }
}