package com.patientappointment.scheduler.utils.enum_converters;

import com.patientappointment.scheduler.utils.enums.DoctorSpecialization;
import org.springframework.core.convert.converter.Converter;

public class StringToSpecializationConverter implements Converter<String, DoctorSpecialization> {

    @Override
    public DoctorSpecialization convert(String source) {
        return DoctorSpecialization.valueOf(source.toUpperCase());
    }
}
