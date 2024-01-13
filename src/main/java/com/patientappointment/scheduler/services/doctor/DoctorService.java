package com.patientappointment.scheduler.services.doctor;

import com.patientappointment.scheduler.models.dtos.DoctorDTO;

public interface DoctorService {

    DoctorDTO createDoctor(DoctorDTO doctorDTO);
}
