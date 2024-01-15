package com.patientappointment.scheduler.models.dtos;

import com.patientappointment.scheduler.models.entities.Doctor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class DoctorScheduleDTO {

    private Long id;

    private LocalDate workingDate;

    private LocalTime startShift;

    private LocalTime endShift;

    private Doctor doctor;
}