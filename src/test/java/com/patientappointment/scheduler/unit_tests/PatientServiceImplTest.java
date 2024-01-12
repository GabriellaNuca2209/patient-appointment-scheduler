package com.patientappointment.scheduler.unit_tests;

import com.patientappointment.scheduler.models.dtos.PatientDTO;
import com.patientappointment.scheduler.models.entities.Patient;
import com.patientappointment.scheduler.repositories.PatientRepository;
import com.patientappointment.scheduler.services.PatientServiceImpl;
import com.patientappointment.scheduler.services.PatientServiceValidationImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientServiceValidationImpl patientServiceValidation;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    void testCreatePatient_ShouldPass() {
        // GIVEN
        Patient patient = new Patient();
        patient.setFirstName("Jane");
        patient.setLastName("Doe");
        patient.setDob(LocalDate.of(2000, 9, 22));
        patient.setEmail("jane@email.com");

        Patient savedPatient = new Patient();
        savedPatient.setId(1L);
        savedPatient.setFirstName("Jane");
        savedPatient.setLastName("Doe");
        savedPatient.setDob(LocalDate.of(2000, 9, 22));
        savedPatient.setEmail("jane@email.com");

        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(1L);
        patientDTO.setFirstName("Jane");
        patientDTO.setLastName("Doe");
        patientDTO.setDob(LocalDate.of(2000, 9, 22));
        patientDTO.setAge((int) ChronoUnit.YEARS.between(patientDTO.getDob(), LocalDate.now()));
        patientDTO.setEmail("jane@email.com");

        when(modelMapper.map(patientDTO, Patient.class)).thenReturn(patient);
        when(modelMapper.map(savedPatient, PatientDTO.class)).thenReturn(patientDTO);
        when(patientRepository.save(patient)).thenReturn(savedPatient);

        // WHEN
        PatientDTO savedPatientDTO = patientService.createPatient(patientDTO);

        // THEN
        verify(patientRepository, times(1)).save(patient);
        assertEquals(patientDTO, savedPatientDTO);
    }
}