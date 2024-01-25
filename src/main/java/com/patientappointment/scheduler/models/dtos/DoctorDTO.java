package com.patientappointment.scheduler.models.dtos;

import com.patientappointment.scheduler.utils.enums.DoctorLocation;
import com.patientappointment.scheduler.utils.enums.DoctorSpecialization;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.patientappointment.scheduler.utils.constants.DTOConstants.REGEX_CHAR_PATTERN;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DoctorDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 75, message = "must be between 3 and 75 characters")
    @Pattern(regexp = REGEX_CHAR_PATTERN, message = "Invalid characters found")
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 75, message = "must be between 3 and 75 characters")
    @Pattern(regexp = REGEX_CHAR_PATTERN, message = "Invalid characters found")
    private String lastName;

    @Email
    private String email;

    private DoctorSpecialization specialization;

    private DoctorLocation location;
}