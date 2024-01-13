package com.patientappointment.scheduler.services.schedule;

import com.patientappointment.scheduler.models.dtos.DoctorScheduleDTO;

public interface ScheduleService {

    DoctorScheduleDTO createSchedule(DoctorScheduleDTO doctorScheduleDTO);
}
