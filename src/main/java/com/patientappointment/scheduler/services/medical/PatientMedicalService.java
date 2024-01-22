package com.patientappointment.scheduler.services.medical;

import com.patientappointment.scheduler.models.dtos.AppointmentDTO;
import com.patientappointment.scheduler.models.dtos.DoctorDTO;
import com.patientappointment.scheduler.models.dtos.DoctorScheduleDTO;
import com.patientappointment.scheduler.models.dtos.PatientDTO;
import com.patientappointment.scheduler.models.entities.Appointment;
import com.patientappointment.scheduler.utils.enums.AppointmentStatus;
import com.patientappointment.scheduler.utils.enums.DoctorLocation;
import com.patientappointment.scheduler.utils.enums.DoctorSpecialization;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface PatientMedicalService {

    List<DoctorDTO> getFilteredDoctors(DoctorSpecialization specialization, DoctorLocation location);
    DoctorDTO getDoctor(Long id);
    List<DoctorScheduleDTO> getDoctorSchedules(Long doctorId);
    List<LocalTime> getAvailableSlots(LocalDate date, Long doctorId, List<Appointment> appointments);
    DoctorScheduleDTO getSchedule(Long scheduleId);
    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO, PatientDTO patientDTO, DoctorDTO doctorDTO);
    List<Appointment> getAppointments(LocalDate date, Long doctorId, AppointmentStatus status);
    List<AppointmentDTO> getPatientAppointments(Long id);
    AppointmentDTO cancelAppointment(Long appointmentId);
}
