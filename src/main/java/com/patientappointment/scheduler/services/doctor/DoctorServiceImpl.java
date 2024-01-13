package com.patientappointment.scheduler.services.doctor;

import com.patientappointment.scheduler.exceptions.doctor.DoctorNotFoundException;
import com.patientappointment.scheduler.models.dtos.DoctorDTO;
import com.patientappointment.scheduler.models.dtos.DoctorScheduleDTO;
import com.patientappointment.scheduler.models.entities.Doctor;
import com.patientappointment.scheduler.repositories.DoctorRepository;
import com.patientappointment.scheduler.services.schedule.ScheduleService;
import com.patientappointment.scheduler.utils.enums.DoctorLocation;
import com.patientappointment.scheduler.utils.enums.DoctorSpecialization;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    private final DoctorServiceValidation doctorServiceValidation;

    private final ScheduleService scheduleService;

    private final ModelMapper modelMapper;

    public DoctorServiceImpl(DoctorRepository doctorRepository, DoctorServiceValidation doctorServiceValidation, ScheduleService scheduleService, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.doctorServiceValidation = doctorServiceValidation;
        this.scheduleService = scheduleService;
        this.modelMapper = modelMapper;
    }

    @Override
    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        doctorServiceValidation.validateDoctorAlreadyExists(doctorDTO);
        Doctor savedDoctor = doctorRepository.save(modelMapper.map(doctorDTO, Doctor.class));
        log.info("Doctor " + doctorDTO.getFirstName() + " " + doctorDTO.getLastName() + " was saved in database");

        return modelMapper.map(savedDoctor, DoctorDTO.class);
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream().map(doctor -> modelMapper.map(doctor, DoctorDTO.class)).toList();
    }

    @Override
    public DoctorScheduleDTO createSchedule(DoctorScheduleDTO doctorScheduleDTO, Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Doctor with id: " + doctorId + " not found"));
        doctorScheduleDTO.setDoctor(doctor);

        return scheduleService.createSchedule(doctorScheduleDTO);
    }

    @Override
    public List<DoctorDTO> getFilteredDoctors(DoctorSpecialization specialization, DoctorLocation location) {
        return doctorRepository.findFilteredDoctors(specialization, location).stream().map(doctor -> modelMapper.map(doctor, DoctorDTO.class)).toList();
    }
}