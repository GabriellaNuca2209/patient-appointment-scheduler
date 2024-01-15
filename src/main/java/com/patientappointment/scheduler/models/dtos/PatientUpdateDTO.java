package com.patientappointment.scheduler.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static com.patientappointment.scheduler.utils.constants.DTOConstants.REGEX_CHAR_PATTERN;

@Data
public class PatientUpdateDTO {

    @Size(min = 3, max = 75, message = "must be between 2 and 75 characters")
    @Pattern(regexp = REGEX_CHAR_PATTERN, message = "Invalid characters found")
    private String firstName;

    @Size(min = 3, max = 75, message = "must be between 2 and 75 characters")
    @Pattern(regexp = REGEX_CHAR_PATTERN, message = "Invalid characters found")
    private String lastName;

    @Email
    private String email;
}
