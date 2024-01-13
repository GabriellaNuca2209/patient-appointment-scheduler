package com.patientappointment.scheduler.services.doctor;

import com.patientappointment.scheduler.models.dtos.DoctorDTO;
import com.patientappointment.scheduler.models.dtos.DoctorScheduleDTO;

public interface DoctorService {

    DoctorDTO createDoctor(DoctorDTO doctorDTO);
    DoctorScheduleDTO createSchedule(DoctorScheduleDTO doctorScheduleDTO, Long doctorId);
}
