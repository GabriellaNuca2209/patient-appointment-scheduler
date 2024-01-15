package com.patientappointment.scheduler.services.schedule;

import com.patientappointment.scheduler.models.dtos.DoctorScheduleDTO;
import com.patientappointment.scheduler.models.entities.DoctorSchedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleService {

    DoctorScheduleDTO createSchedule(DoctorScheduleDTO doctorScheduleDTO);
    List<DoctorScheduleDTO> getDoctorSchedule(Long doctorId);
    DoctorSchedule getSchedule(LocalDate date, Long id);
    List<LocalTime> getAvailableSlots(LocalDate date, Long doctorId);
}
