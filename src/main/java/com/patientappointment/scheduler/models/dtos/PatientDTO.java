package com.patientappointment.scheduler.models.dtos;

import com.patientappointment.scheduler.models.entities.Appointment;
import com.patientappointment.scheduler.models.entities.Doctor;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class PatientDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 75, message = "must be between 2 and 75 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Invalid characters found")
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 75, message = "must be between 2 and 75 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Invalid characters found")
    private String lastName;

    @NotNull
    private LocalDate dob;

    @Email
    private String email;
}