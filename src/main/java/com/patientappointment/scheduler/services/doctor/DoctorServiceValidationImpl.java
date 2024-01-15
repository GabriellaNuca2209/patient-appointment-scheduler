package com.patientappointment.scheduler.services.doctor;

import com.patientappointment.scheduler.exceptions.doctor.DoctorAlreadyExistsException;
import com.patientappointment.scheduler.models.dtos.DoctorDTO;
import com.patientappointment.scheduler.models.entities.Doctor;
import com.patientappointment.scheduler.repositories.doctor.DoctorRepository;
import org.springframework.stereotype.Component;

@Component
public class DoctorServiceValidationImpl implements DoctorServiceValidation {

    private final DoctorRepository doctorRepository;

    public DoctorServiceValidationImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public void validateDoctorAlreadyExists(DoctorDTO doctorDTO) {
        Doctor foundDoctor = doctorRepository.findByEmail(doctorDTO.getEmail());

        if (foundDoctor != null) throw new DoctorAlreadyExistsException("Doctor with email " + doctorDTO.getEmail() + " already exists");
    }
}
