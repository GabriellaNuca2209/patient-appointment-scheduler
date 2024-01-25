package com.patientappointment.scheduler.unit_tests;

import com.patientappointment.scheduler.models.dtos.DoctorDTO;
import com.patientappointment.scheduler.models.entities.Doctor;
import com.patientappointment.scheduler.repositories.doctor.DoctorRepository;
import com.patientappointment.scheduler.services.doctor.DoctorServiceImpl;
import com.patientappointment.scheduler.services.doctor.DoctorServiceValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static com.patientappointment.scheduler.utils.enums.DoctorLocation.CLINIC_A;
import static com.patientappointment.scheduler.utils.enums.DoctorSpecialization.CARDIOLOGY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
        Doctor doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@email.com")
                .specialization(CARDIOLOGY)
                .location(CLINIC_A)
                .build();

        Doctor savedDoctor = Doctor.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@email.com")
                .specialization(CARDIOLOGY)
                .location(CLINIC_A)
                .build();

        DoctorDTO requestDoctorDTO = DoctorDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@email.com")
                .specialization(CARDIOLOGY)
                .location(CLINIC_A)
                .build();

        DoctorDTO responseDoctorDTO = DoctorDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@email.com")
                .specialization(CARDIOLOGY)
                .location(CLINIC_A)
                .build();

        when(modelMapper.map(requestDoctorDTO, Doctor.class)).thenReturn(doctor);
        when(modelMapper.map(savedDoctor, DoctorDTO.class)).thenReturn(responseDoctorDTO);
        when(doctorRepository.save(doctor)).thenReturn(savedDoctor);

        // WHEN
        DoctorDTO savedDoctorDTO = doctorService.createDoctor(requestDoctorDTO);

        // THEN
        verify(doctorRepository, times(1)).save(doctor);
        assertEquals(responseDoctorDTO, savedDoctorDTO);
    }
}









