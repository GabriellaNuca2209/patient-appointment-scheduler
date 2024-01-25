package com.patientappointment.scheduler.utils.enum_converters;

import com.patientappointment.scheduler.utils.enums.DoctorSpecialization;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class DoctorSpecializationConverter implements AttributeConverter<DoctorSpecialization, String> {

    @Override
    public String convertToDatabaseColumn(DoctorSpecialization doctorSpecialization) {
        if (doctorSpecialization == null) return null;

        return doctorSpecialization.getSpecializationLabel();
    }

    @Override
    public DoctorSpecialization convertToEntityAttribute(String specializationLabel) {
        if (specializationLabel == null) return null;

        return Stream.of(DoctorSpecialization.values())
                .filter(s -> s.getSpecializationLabel().equals(specializationLabel))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}