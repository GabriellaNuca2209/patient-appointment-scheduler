package com.patientappointment.scheduler.services.patient;

import com.patientappointment.scheduler.models.dtos.AppointmentDTO;
import com.patientappointment.scheduler.models.dtos.DoctorDTO;
import com.patientappointment.scheduler.models.dtos.DoctorScheduleDTO;
import com.patientappointment.scheduler.models.dtos.PatientDTO;
import com.patientappointment.scheduler.utils.enums.DoctorLocation;
import com.patientappointment.scheduler.utils.enums.DoctorSpecialization;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface PatientService {

    PatientDTO createPatient(PatientDTO patientDTO);
    List<PatientDTO> getAllPatients();
    PatientDTO getPatient(Long id);
    PatientDTO updatePatient(Long id, PatientDTO patientDTO);
    void deletePatient(Long id);
    List<DoctorDTO> getFilteredDoctors(DoctorSpecialization specialization, DoctorLocation location);
    List<DoctorScheduleDTO> getDoctorSchedules(Long doctorId);
    List<LocalTime> getAvailableSlots(LocalDate date, Long doctorId);
    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO, Long patientId, Long doctorId);
}
