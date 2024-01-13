package com.patientappointment.scheduler.services.doctor;

import com.patientappointment.scheduler.models.dtos.DoctorDTO;
import com.patientappointment.scheduler.models.entities.Doctor;
import com.patientappointment.scheduler.repositories.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    private final DoctorServiceValidation doctorServiceValidation;

    private final ModelMapper modelMapper;

    public DoctorServiceImpl(DoctorRepository doctorRepository, DoctorServiceValidation doctorServiceValidation, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.doctorServiceValidation = doctorServiceValidation;
        this.modelMapper = modelMapper;
    }

    @Override
    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        doctorServiceValidation.validateDoctorAlreadyExists(doctorDTO);
        Doctor savedDoctor = doctorRepository.save(modelMapper.map(doctorDTO, Doctor.class));
        log.info("Doctor " + doctorDTO.getFirstName() + " " + doctorDTO.getLastName() + " was saved in database");

        return modelMapper.map(savedDoctor, DoctorDTO.class);
    }
}