package com.patientappointment.scheduler.controllers;

import com.patientappointment.scheduler.models.dtos.DoctorDTO;
import com.patientappointment.scheduler.models.dtos.DoctorScheduleDTO;
import com.patientappointment.scheduler.services.doctor.DoctorService;
import com.patientappointment.scheduler.utils.enums.DoctorLocation;
import com.patientappointment.scheduler.utils.enums.DoctorSpecialization;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllAndFilteredDoctors(@RequestParam(value = "specialization", required = false) DoctorSpecialization specialization,
                                                              @RequestParam(value = "location", required = false) DoctorLocation location) {
        return ResponseEntity.ok(doctorService.getFilteredDoctors(specialization, location));
    }

    @PostMapping("/{doctorId}/schedules")
    public ResponseEntity<DoctorScheduleDTO> createSchedule(@Valid @RequestBody DoctorScheduleDTO doctorScheduleDTO, @PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.createSchedule(doctorScheduleDTO, doctorId));
    }

    @GetMapping("/{doctorId}/schedules")
    public ResponseEntity<List<DoctorScheduleDTO>> getDoctorSchedules(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.getDoctorSchedules(doctorId));
    }
}