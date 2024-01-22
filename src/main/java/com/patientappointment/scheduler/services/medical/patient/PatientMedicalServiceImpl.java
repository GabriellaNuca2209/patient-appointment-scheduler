package com.patientappointment.scheduler.services.medical.patient;

import com.patientappointment.scheduler.models.dtos.AppointmentDTO;
import com.patientappointment.scheduler.models.dtos.DoctorDTO;
import com.patientappointment.scheduler.models.dtos.DoctorScheduleDTO;
import com.patientappointment.scheduler.models.dtos.PatientDTO;
import com.patientappointment.scheduler.models.entities.Appointment;
import com.patientappointment.scheduler.services.appointment.AppointmentService;
import com.patientappointment.scheduler.services.doctor.DoctorService;
import com.patientappointment.scheduler.services.schedule.ScheduleService;
import com.patientappointment.scheduler.utils.enums.AppointmentStatus;
import com.patientappointment.scheduler.utils.enums.DoctorLocation;
import com.patientappointment.scheduler.utils.enums.DoctorSpecialization;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class PatientMedicalServiceImpl implements PatientMedicalService {

    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final ScheduleService scheduleService;

    public PatientMedicalServiceImpl(DoctorService doctorService, AppointmentService appointmentService, ScheduleService scheduleService) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.scheduleService = scheduleService;
    }

    @Override
    public List<DoctorDTO> getFilteredDoctors(DoctorSpecialization specialization, DoctorLocation location) {
        return doctorService.getFilteredDoctors(specialization, location);
    }

    @Override
    public DoctorDTO getDoctor(Long id) {
        return doctorService.getDoctor(id);
    }

    @Override
    public List<DoctorScheduleDTO> getDoctorSchedules(Long doctorId) {
        return doctorService.getDoctorSchedules(doctorId);
    }

    @Override
    public List<LocalTime> getAvailableSlots(LocalDate date, Long doctorId, List<Appointment> appointments) {
        return scheduleService.getAvailableSlots(date, doctorId, appointments);
    }

    @Override
    public DoctorScheduleDTO getSchedule(Long scheduleId) {
        return scheduleService.getSchedule(scheduleId);
    }

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO, PatientDTO patientDTO, DoctorDTO doctorDTO) {
        return appointmentService.createAppointment(appointmentDTO, patientDTO, doctorDTO);
    }

    @Override
    public List<Appointment> getAppointments(LocalDate date, Long doctorId, AppointmentStatus status) {
        return appointmentService.getAppointments(date, doctorId, status);
    }

    @Override
    public List<AppointmentDTO> getPatientAppointments(Long id) {
        return appointmentService.getPatientAppointments(id);
    }

    @Override
    public AppointmentDTO cancelAppointment(Long appointmentId) {
        return appointmentService.cancelAppointment(appointmentId);
    }
}
