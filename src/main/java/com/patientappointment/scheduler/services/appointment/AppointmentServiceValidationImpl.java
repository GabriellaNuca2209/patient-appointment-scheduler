package com.patientappointment.scheduler.services.appointment;

import com.patientappointment.scheduler.exceptions.appointment.DateNotFoundException;
import com.patientappointment.scheduler.exceptions.appointment.TimeNotFoundException;
import com.patientappointment.scheduler.models.entities.Appointment;
import com.patientappointment.scheduler.models.entities.DoctorSchedule;
import com.patientappointment.scheduler.repositories.AppointmentRepository;
import com.patientappointment.scheduler.repositories.ScheduleRepository;
import com.patientappointment.scheduler.services.patient.PatientService;
import com.patientappointment.scheduler.services.schedule.ScheduleService;
import com.patientappointment.scheduler.utils.enums.AppointmentStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class AppointmentServiceValidationImpl implements AppointmentServiceValidation {

    private final AppointmentRepository appointmentRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleService scheduleService;


    public AppointmentServiceValidationImpl(AppointmentRepository appointmentRepository, ScheduleRepository scheduleRepository, ScheduleService scheduleService) {
        this.appointmentRepository = appointmentRepository;
        this.scheduleRepository = scheduleRepository;
        this.scheduleService = scheduleService;
    }

    @Override
    public void validateAppointmentDate(LocalDate date, Long doctorId) {
        DoctorSchedule schedule = scheduleRepository.findByWorkingDateAndDoctorId(date, doctorId);
        if (schedule == null) throw new DateNotFoundException("Doctor's schedule with date " + date + " not found.");
    }

    @Override
    public void validateAppointmentTime(LocalDate date, LocalTime time, Long doctorId) {
        List<Appointment> appointments = appointmentRepository.findByAppointmentDateAndDoctorIdAndStatus(date, doctorId, AppointmentStatus.SCHEDULED);
        List<LocalTime> slots = scheduleService.getAvailableSlots(date, doctorId, appointments);
        if (!slots.contains(time)) throw new TimeNotFoundException("Doctor's schedule with time " + time + " not found");
    }
}
