package com.patientappointment.scheduler.services.medical.doctor;

import com.patientappointment.scheduler.models.dtos.AppointmentDTO;
import com.patientappointment.scheduler.models.dtos.DoctorScheduleDTO;
import com.patientappointment.scheduler.models.entities.Appointment;
import com.patientappointment.scheduler.services.appointment.AppointmentService;
import com.patientappointment.scheduler.services.schedule.ScheduleService;
import com.patientappointment.scheduler.utils.enums.AppointmentStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DoctorMedicalServiceImpl implements DoctorMedicalService {

    private final ScheduleService scheduleService;
    private final AppointmentService appointmentService;

    public DoctorMedicalServiceImpl(ScheduleService scheduleService, AppointmentService appointmentService) {
        this.scheduleService = scheduleService;
        this.appointmentService = appointmentService;
    }

    @Override
    public DoctorScheduleDTO createSchedule(DoctorScheduleDTO doctorScheduleDTO) {
        return scheduleService.createSchedule(doctorScheduleDTO);
    }

    @Override
    public void deleteSchedule(Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
    }

    @Override
    public DoctorScheduleDTO getSchedule(Long scheduleId) {
        return scheduleService.getSchedule(scheduleId);
    }

    @Override
    public List<DoctorScheduleDTO> getDoctorSchedules(Long doctorId) {
        return scheduleService.getDoctorSchedules(doctorId);
    }

    @Override
    public AppointmentDTO openConsultation(Long appointmentId) {
        return appointmentService.openConsultation(appointmentId);
    }

    @Override
    public AppointmentDTO closeConsultation(Long appointmentId) {
        return appointmentService.closeConsultation(appointmentId);
    }

    @Override
    public void cancelDoctorAppointment(Appointment appointment) {
        appointmentService.cancelDoctorAppointment(appointment);
    }

    @Override
    public List<Appointment> getAppointments(LocalDate date, Long doctorId, AppointmentStatus status) {
        return appointmentService.getAppointments(date, doctorId, status);
    }
}
