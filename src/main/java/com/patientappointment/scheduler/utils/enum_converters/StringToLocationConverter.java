package com.patientappointment.scheduler.utils.enum_converters;

import com.patientappointment.scheduler.utils.enums.DoctorLocation;
import org.springframework.core.convert.converter.Converter;

public class StringToLocationConverter implements Converter<String, DoctorLocation> {

    @Override
    public DoctorLocation convert(String source) {
        return DoctorLocation.valueOf(source.toUpperCase());
    }
}
