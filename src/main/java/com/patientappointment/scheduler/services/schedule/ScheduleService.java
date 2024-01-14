package com.patientappointment.scheduler.services.schedule;

import com.patientappointment.scheduler.models.dtos.DoctorScheduleDTO;

import java.util.List;

public interface ScheduleService {

    DoctorScheduleDTO createSchedule(DoctorScheduleDTO doctorScheduleDTO);
    List<DoctorScheduleDTO> getDoctorSchedule(Long doctorId);
}
