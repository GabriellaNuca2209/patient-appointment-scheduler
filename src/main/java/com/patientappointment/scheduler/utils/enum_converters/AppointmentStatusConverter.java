package com.patientappointment.scheduler.utils.enum_converters;

import com.patientappointment.scheduler.utils.enums.AppointmentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class AppointmentStatusConverter implements AttributeConverter<AppointmentStatus, String> {

    @Override
    public String convertToDatabaseColumn(AppointmentStatus appointmentStatus) {
        if (appointmentStatus == null) return null;

        return appointmentStatus.getStatusLabel();
    }

    @Override
    public AppointmentStatus convertToEntityAttribute(String statusLabel) {
        if (statusLabel == null) return null;

        return Stream.of(AppointmentStatus.values())
                .filter(s -> s.getStatusLabel().equals(statusLabel))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}