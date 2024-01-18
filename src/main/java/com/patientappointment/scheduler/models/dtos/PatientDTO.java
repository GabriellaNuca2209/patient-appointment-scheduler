package com.patientappointment.scheduler.models.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.patientappointment.scheduler.utils.constants.DTOConstants.REGEX_CHAR_PATTERN;

@Builder
@NoArgsConstructor
@AllArgsConstructor
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