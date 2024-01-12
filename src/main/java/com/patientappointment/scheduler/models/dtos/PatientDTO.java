package com.patientappointment.scheduler.models.dtos;

import com.patientappointment.scheduler.models.entities.Appointment;
import com.patientappointment.scheduler.models.entities.Doctor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class PatientDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 75, message = "must be between 2 and 75 characters")
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 75, message = "must be between 2 and 75 characters")
    private String lastName;

    @NotNull
    private LocalDate dob;

    private Integer age;

    @Email
    private String email;

    private Set<Doctor> doctors = new HashSet<>();

    private Set<Appointment> appointments = new HashSet<>();
}