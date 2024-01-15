package com.patientappointment.scheduler.controllers;

import com.patientappointment.scheduler.models.dtos.DoctorDTO;
import com.patientappointment.scheduler.models.dtos.DoctorScheduleDTO;
import com.patientappointment.scheduler.services.doctor.DoctorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
@Validated
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<DoctorDTO> createDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
        return ResponseEntity.ok(doctorService.createDoctor(doctorDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctor(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctor(id));
    }

    @PostMapping("/{doctorId}/schedules")
    public ResponseEntity<DoctorScheduleDTO> createSchedule(@Valid @RequestBody DoctorScheduleDTO doctorScheduleDTO, @PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.createSchedule(doctorScheduleDTO, doctorId));
    }
}