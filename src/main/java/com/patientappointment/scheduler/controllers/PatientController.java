package com.patientappointment.scheduler.controllers;

import com.patientappointment.scheduler.models.dtos.*;
import com.patientappointment.scheduler.services.patient.PatientService;
import com.patientappointment.scheduler.utils.enums.DoctorLocation;
import com.patientappointment.scheduler.utils.enums.DoctorSpecialization;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("api/patients")
@Validated
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientDTO patientDTO) {
        return ResponseEntity.ok(patientService.createPatient(patientDTO));
    }

    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatient(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientUpdateDTO patientUpdateDTO) {
        return ResponseEntity.ok(patientService.updatePatient(id, patientUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }

    @GetMapping("/filtered/doctors")
    public ResponseEntity<List<DoctorDTO>> getAllAndFilteredDoctors(@RequestParam(value = "specialization", required = false) DoctorSpecialization specialization,
                                                                    @RequestParam(value = "location", required = false) DoctorLocation location) {
        return ResponseEntity.ok(patientService.getFilteredDoctors(specialization, location));
    }

    @GetMapping("/doctors/{doctorId}/schedules")
    public ResponseEntity<List<DoctorScheduleDTO>> getDoctorSchedules(@PathVariable Long doctorId) {
        return ResponseEntity.ok(patientService.getDoctorSchedules(doctorId));
    }

    @GetMapping("/{date}/doctors/{doctorId}/slots")
    public ResponseEntity<List<LocalTime>> getAvailableSlots(@PathVariable LocalDate date, @PathVariable Long doctorId) {
        return ResponseEntity.ok(patientService.getAvailableSlots(date, doctorId));
    }

    @PostMapping("/{patientId}/doctors/{doctorId}/appointments")
    public ResponseEntity<AppointmentDTO> createAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO,
                                                            @PathVariable Long patientId,
                                                            @PathVariable Long doctorId) {
        return ResponseEntity.ok(patientService.createAppointment(appointmentDTO, patientId, doctorId));
    }

    @GetMapping("/{id}/appointments")
    public ResponseEntity<List<AppointmentDTO>> getPatientAppointments(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientAppointments(id));
    }
}