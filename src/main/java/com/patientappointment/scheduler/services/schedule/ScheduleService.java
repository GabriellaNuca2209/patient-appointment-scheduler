package com.patientappointment.scheduler.services.schedule;

import com.patientappointment.scheduler.models.dtos.DoctorScheduleDTO;
import com.patientappointment.scheduler.models.entities.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleService {

    DoctorScheduleDTO createSchedule(DoctorScheduleDTO doctorScheduleDTO);
    void deleteSchedule(Long scheduleId);
    DoctorScheduleDTO getSchedule(Long scheduleId);
    List<DoctorScheduleDTO> getDoctorSchedules(Long doctorId);
    List<LocalTime> getAvailableSlots(LocalDate date, Long doctorId, List<Appointment> appointments);
}
