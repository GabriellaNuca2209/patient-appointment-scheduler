package com.patientappointment.scheduler.services.schedule;

import com.patientappointment.scheduler.models.dtos.DoctorScheduleDTO;
import com.patientappointment.scheduler.models.entities.Appointment;
import com.patientappointment.scheduler.models.entities.DoctorSchedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleService {

    DoctorScheduleDTO createSchedule(DoctorScheduleDTO doctorScheduleDTO);
    List<DoctorScheduleDTO> getDoctorSchedules(Long doctorId);
    List<LocalTime> getAvailableSlots(LocalDate date, Long doctorId, List<Appointment> appointments);
}
