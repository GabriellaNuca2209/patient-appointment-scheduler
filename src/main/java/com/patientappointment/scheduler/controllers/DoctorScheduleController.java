package com.patientappointment.scheduler.controllers;

import com.patientappointment.scheduler.services.schedule.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class DoctorScheduleController {

    private final ScheduleService scheduleService;

    public DoctorScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/{date}/doctors/{doctorId}")
    public ResponseEntity<List<LocalTime>> getAvailableSlots(@PathVariable LocalDate date, @PathVariable Long doctorId) {
        return ResponseEntity.ok(scheduleService.getAvailableSlots(date, doctorId));
    }
}
