package com.patientappointment.scheduler.services.doctor;

import com.patientappointment.scheduler.models.dtos.AppointmentDTO;
import com.patientappointment.scheduler.models.dtos.DoctorDTO;
import com.patientappointment.scheduler.models.dtos.DoctorScheduleDTO;
import com.patientappointment.scheduler.models.entities.Appointment;
import com.patientappointment.scheduler.utils.enums.DoctorLocation;
import com.patientappointment.scheduler.utils.enums.DoctorSpecialization;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DoctorService {

    DoctorDTO createDoctor(DoctorDTO doctorDTO);
    List<DoctorDTO> getAllDoctors();
    DoctorDTO getDoctor(Long id);
    DoctorScheduleDTO createSchedule(DoctorScheduleDTO doctorScheduleDTO, Long doctorId);
    void deleteSchedule(Long doctorId, Long scheduleId);
    List<DoctorDTO> getFilteredDoctors(DoctorSpecialization specialization, DoctorLocation location);
    List<DoctorScheduleDTO> getDoctorSchedules(Long doctorId);
    AppointmentDTO openConsultation(Long doctorId, Long appointmentId);
    AppointmentDTO closeConsultation(Long doctorId, Long appointmentId);
}
