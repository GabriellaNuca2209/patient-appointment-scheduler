package com.patientappointment.scheduler.models.dtos;

import com.patientappointment.scheduler.utils.enums.DoctorLocation;
import com.patientappointment.scheduler.utils.enums.DoctorSpecialization;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}