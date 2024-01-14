package com.patientappointment.scheduler.models.dtos;

import com.patientappointment.scheduler.models.entities.Doctor;
import com.patientappointment.scheduler.models.entities.Patient;
import com.patientappointment.scheduler.utils.enums.AppointmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentDTO {

    private Long id;

    @NotNull
    private LocalDate appointmentDate;

    @NotNull
    private LocalTime appointmentTime;

    private AppointmentStatus status;

    private Patient patient;

    private Doctor doctor;
}