package com.patientappointment.scheduler.models.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

import static com.patientappointment.scheduler.utils.constants.RegexConstants.REGEX_CHAR_PATTERN;

@Data
public class PatientDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 75, message = "must be between 2 and 75 characters")
    @Pattern(regexp = REGEX_CHAR_PATTERN, message = "Invalid characters found")
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 75, message = "must be between 2 and 75 characters")
    @Pattern(regexp = REGEX_CHAR_PATTERN, message = "Invalid characters found")
    private String lastName;

    @NotNull
    private LocalDate dob;

    @Email
    private String email;
}