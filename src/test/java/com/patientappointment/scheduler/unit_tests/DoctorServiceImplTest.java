package com.patientappointment.scheduler.unit_tests;

import com.patientappointment.scheduler.models.dtos.DoctorDTO;
import com.patientappointment.scheduler.models.entities.Doctor;
import com.patientappointment.scheduler.repositories.doctor.DoctorRepository;
import com.patientappointment.scheduler.services.doctor.DoctorServiceImpl;
import com.patientappointment.scheduler.services.doctor.DoctorServiceValidation;
import com.patientappointment.scheduler.utils.enums.DoctorLocation;
import com.patientappointment.scheduler.utils.enums.DoctorSpecialization;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class DoctorServiceImplTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DoctorServiceValidation doctorServiceValidation;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @Test
    void testCreateDoctor_ShouldPass() {
        // GIVEN
        Doctor doctor = new Doctor();
        doctor.setFirstName("John");
        doctor.setLastName("Doe");
        doctor.setEmail("john@email.com");
        doctor.setSpecialization(DoctorSpecialization.CARDIOLOGY);
        doctor.setLocation(DoctorLocation.CLINIC_A);

        Doctor savedDoctor = new Doctor();
        savedDoctor.setFirstName("John");
        savedDoctor.setLastName("Doe");
        savedDoctor.setEmail("john@email.com");
        savedDoctor.setSpecialization(DoctorSpecialization.CARDIOLOGY);
        savedDoctor.setLocation(DoctorLocation.CLINIC_A);

        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setFirstName("John");
        doctorDTO.setLastName("Doe");
        doctorDTO.setEmail("john@email.com");
        doctorDTO.setSpecialization(DoctorSpecialization.CARDIOLOGY);
        doctorDTO.setLocation(DoctorLocation.CLINIC_A);

        when(modelMapper.map(doctorDTO, Doctor.class)).thenReturn(doctor);
        when(modelMapper.map(savedDoctor, DoctorDTO.class)).thenReturn(doctorDTO);
        when(doctorRepository.save(doctor)).thenReturn(savedDoctor);

        // WHEN
        DoctorDTO savedDoctorDTO = doctorService.createDoctor(doctorDTO);

        // THEN
        verify(doctorRepository, times(1)).save(doctor);
        assertEquals(doctorDTO, savedDoctorDTO);
    }
}









