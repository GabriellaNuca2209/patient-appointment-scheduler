package com.patientappointment.scheduler.services.appointment;

import com.patientappointment.scheduler.exceptions.appointment.DateNotFoundException;
import com.patientappointment.scheduler.exceptions.appointment.TimeNotFoundException;
import com.patientappointment.scheduler.models.dtos.DoctorDTO;
import com.patientappointment.scheduler.models.entities.DoctorSchedule;
import com.patientappointment.scheduler.services.schedule.ScheduleService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class AppointmentServiceValidatorImpl implements AppointmentServiceValidator {

    private final ScheduleService scheduleService;

    public AppointmentServiceValidatorImpl(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Override
    public void validateAppointmentDate(LocalDate date, DoctorDTO doctorDTO) {
        DoctorSchedule schedule = scheduleService.getSchedule(date, doctorDTO.getId());

        if (schedule == null) throw new DateNotFoundException("Date " + date + " not valid.");
    }

    @Override
    public void validateAppointmentTime(LocalDate date, LocalTime time, DoctorDTO doctorDTO) {
        DoctorSchedule schedule = scheduleService.getSchedule(date, doctorDTO.getId());
        List<LocalTime> availableSlots = schedule.getAvailableSlots();

        if (!availableSlots.contains(time)) throw new TimeNotFoundException("Time " + time + " not valid");
    }
}