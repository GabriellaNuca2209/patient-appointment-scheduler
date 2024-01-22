package com.patientappointment.scheduler.services.medical.doctor;

import com.patientappointment.scheduler.models.dtos.AppointmentDTO;
import com.patientappointment.scheduler.models.dtos.DoctorScheduleDTO;
import com.patientappointment.scheduler.models.entities.Appointment;
import com.patientappointment.scheduler.utils.enums.AppointmentStatus;

import java.time.LocalDate;
import java.util.List;

public interface DoctorMedicalService {

    DoctorScheduleDTO createSchedule(DoctorScheduleDTO doctorScheduleDTO);
    void deleteSchedule(Long scheduleId);
    DoctorScheduleDTO getSchedule(Long scheduleId);
    List<DoctorScheduleDTO> getDoctorSchedules(Long doctorId);
    AppointmentDTO openConsultation(Long appointmentId);
    AppointmentDTO closeConsultation(Long appointmentId);
    void cancelDoctorAppointment(Appointment appointment);
    List<Appointment> getAppointments(LocalDate date, Long doctorId, AppointmentStatus status);
}
