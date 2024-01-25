package com.patientappointment.scheduler.config;

import com.patientappointment.scheduler.utils.enum_converters.StringToLocationConverter;
import com.patientappointment.scheduler.utils.enum_converters.StringToSpecializationConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToSpecializationConverter());
        registry.addConverter(new StringToLocationConverter());
    }
}
