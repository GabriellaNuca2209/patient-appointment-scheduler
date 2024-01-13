package com.patientappointment.scheduler.models.dtos;

import com.patientappointment.scheduler.models.entities.Appointment;
import com.patientappointment.scheduler.models.entities.DoctorSchedule;
import com.patientappointment.scheduler.models.entities.Patient;
import com.patientappointment.scheduler.utils.enums.DoctorLocation;
import com.patientappointment.scheduler.utils.enums.DoctorSpecialization;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class DoctorDTO {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 75, message = "must be between 2 and 75 characters")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 75, message = "must be between 2 and 75 characters")
    private String lastName;

    @Email
    private String email;

    private DoctorSpecialization specialization;

    private DoctorLocation location;

    private Set<Patient> patients = new HashSet<>();

    private Set<Appointment> appointments = new HashSet<>();
}