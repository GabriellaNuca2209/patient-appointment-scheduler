package com.patientappointment.scheduler.unit_tests;

import com.patientappointment.scheduler.models.dtos.PatientDTO;
import com.patientappointment.scheduler.models.entities.Patient;
import com.patientappointment.scheduler.repositories.PatientRepository;
import com.patientappointment.scheduler.services.patient.PatientServiceImpl;
import com.patientappointment.scheduler.services.patient.PatientServiceValidation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientServiceValidation patientServiceValidation;

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

    @Test
    void testGetAllPatients_ShouldPass() {
        // GIVEN
        PatientDTO expectedPatientDTO1 = new PatientDTO();
        expectedPatientDTO1.setId(1L);
        expectedPatientDTO1.setFirstName("Jane");
        expectedPatientDTO1.setLastName("Doe");
        expectedPatientDTO1.setDob(LocalDate.of(2000, 9, 22));
        expectedPatientDTO1.setEmail("jane@email.com");

        PatientDTO expectedPatientDTO2 = new PatientDTO();
        expectedPatientDTO2.setId(2L);
        expectedPatientDTO2.setFirstName("John");
        expectedPatientDTO2.setLastName("Doe");
        expectedPatientDTO2.setDob(LocalDate.of(1999, 9, 22));
        expectedPatientDTO2.setEmail("john@email.com");

        Patient patient1 = new Patient();
        patient1.setId(1L);
        patient1.setFirstName("Jane");
        patient1.setLastName("Doe");
        patient1.setDob(LocalDate.of(2000, 9, 22));
        patient1.setEmail("jane@email.com");

        Patient patient2 = new Patient();
        patient2.setId(2L);
        patient2.setFirstName("John");
        patient2.setLastName("Doe");
        patient2.setDob(LocalDate.of(1999, 9, 22));
        patient2.setEmail("john@email.com");

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