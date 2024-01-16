package com.patientappointment.scheduler.services.appointment;

import com.patientappointment.scheduler.models.dtos.AppointmentDTO;
import com.patientappointment.scheduler.models.dtos.DoctorDTO;
import com.patientappointment.scheduler.models.dtos.PatientDTO;
import com.patientappointment.scheduler.models.entities.Appointment;
import com.patientappointment.scheduler.utils.enums.AppointmentStatus;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO, PatientDTO patientDTO, DoctorDTO doctorDTO);
    List<Appointment> getAppointments(LocalDate date, Long doctorId, AppointmentStatus status);
    List<AppointmentDTO> getPatientAppointments(Long id);
    AppointmentDTO cancelAppointment(Long appointmentId);
}
