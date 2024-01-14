package com.patientappointment.scheduler.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "schedules")
public class DoctorSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "working_date")
    private LocalDate workingDate;

    @Column(name = "start_shift")
    private LocalTime startShift;

    @Column(name = "end_shift")
    private LocalTime endShift;

    private List<LocalTime> availableSlots = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}