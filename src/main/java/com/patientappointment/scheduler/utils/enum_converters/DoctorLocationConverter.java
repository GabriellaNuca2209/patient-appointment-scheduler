package com.patientappointment.scheduler.utils.enum_converters;

import com.patientappointment.scheduler.utils.enums.DoctorLocation;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class DoctorLocationConverter implements AttributeConverter<DoctorLocation, String> {

    @Override
    public String convertToDatabaseColumn(DoctorLocation doctorLocation) {
        if (doctorLocation == null) return null;

        return doctorLocation.getLocationLabel();
    }

    @Override
    public DoctorLocation convertToEntityAttribute(String locationLabel) {
        if (locationLabel == null) return null;

        return Stream.of(DoctorLocation.values())
                .filter(s -> s.getLocationLabel().equals(locationLabel))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}