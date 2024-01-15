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


}