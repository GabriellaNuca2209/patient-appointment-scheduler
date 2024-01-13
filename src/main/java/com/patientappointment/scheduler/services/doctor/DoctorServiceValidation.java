package com.patientappointment.scheduler.services.doctor;

import com.patientappointment.scheduler.models.dtos.DoctorDTO;

public interface DoctorServiceValidation {

    void validateDoctorAlreadyExists(DoctorDTO doctorDTO);
}
