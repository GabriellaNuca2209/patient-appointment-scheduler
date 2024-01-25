package com.patientappointment.scheduler.unit_tests;

import com.patientappointment.scheduler.models.dtos.PatientDTO;
import com.patientappointment.scheduler.models.entities.Patient;
import com.patientappointment.scheduler.repositories.PatientRepository;
import com.patientappointment.scheduler.services.email.EmailService;
import com.patientappointment.scheduler.services.patient.PatientServiceImpl;
import com.patientappointment.scheduler.services.patient.PatientServiceValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientServiceValidation patientServiceValidation;

    @Mock
    private EmailService emailService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    void testCreatePatient_ShouldPass() {
        // GIVEN
        Patient patient = Patient.builder()
                .firstName("Jane")
                .lastName("Doe")
                .dob(LocalDate.of(2000, 9, 22))
                .email("jane@email.com")
                .build();

        Patient savedPatient = Patient.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Doe")
                .dob(LocalDate.of(2000, 9, 22))
                .email("jane@email.com")
                .build();

        PatientDTO requestPatientDTO = PatientDTO.builder()
                .firstName("Jane")
                .lastName("Doe")
                .dob(LocalDate.of(2000, 9, 22))
                .email("jane@email.com")
                .build();

        PatientDTO responsePatientDTO = PatientDTO.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Doe")
                .dob(LocalDate.of(2000, 9, 22))
                .email("jane@email.com")
                .build();

        when(modelMapper.map(requestPatientDTO, Patient.class)).thenReturn(patient);
        when(modelMapper.map(savedPatient, PatientDTO.class)).thenReturn(responsePatientDTO);
        when(patientRepository.save(patient)).thenReturn(savedPatient);

        // WHEN
        PatientDTO savedPatientDTO = patientService.createPatient(requestPatientDTO);

        // THEN
        verify(patientRepository, times(1)).save(patient);
        assertEquals(responsePatientDTO, savedPatientDTO);
    }

    @Test
    void testGetAllPatients_ShouldPass() {
        // GIVEN
        PatientDTO expectedPatientDTO1 = PatientDTO.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Doe")
                .dob(LocalDate.of(2000, 9, 22))
                .email("jane@email.com")
                .build();

        PatientDTO expectedPatientDTO2 = PatientDTO.builder()
                .id(2L)
                .firstName("John")
                .lastName("Doe")
                .dob(LocalDate.of(1999, 9, 22))
                .email("john@email.com")
                .build();

        Patient patient1 = Patient.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Doe")
                .dob(LocalDate.of(2000, 9, 22))
                .email("jane@email.com")
                .build();

        Patient patient2 = Patient.builder()
                .id(2L)
                .firstName("John")
                .lastName("Doe")
                .dob(LocalDate.of(1999, 9, 22))
                .email("john@email.com")
                .build();

        List<PatientDTO> expectedPatientDTOList = new ArrayList<>(List.of(expectedPatientDTO1, expectedPatientDTO2));
        List<Patient> patientList = new ArrayList<>(List.of(patient1, patient2));

        when(patientRepository.findAll()).thenReturn(patientList);
        when(modelMapper.map(patient1, PatientDTO.class)).thenReturn(expectedPatientDTO1);
        when(modelMapper.map(patient2, PatientDTO.class)).thenReturn(expectedPatientDTO2);

        // WHEN
        List<PatientDTO> returnedPatientDTOList = patientService.getAllPatients();

        // THEN
        assertEquals(expectedPatientDTOList, returnedPatientDTOList);
    }
}