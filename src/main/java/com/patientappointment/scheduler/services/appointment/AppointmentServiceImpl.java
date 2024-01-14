package com.patientappointment.scheduler.services.appointment;

import com.patientappointment.scheduler.models.dtos.AppointmentDTO;
import com.patientappointment.scheduler.models.dtos.DoctorDTO;
import com.patientappointment.scheduler.models.dtos.PatientDTO;
import com.patientappointment.scheduler.models.entities.Appointment;
import com.patientappointment.scheduler.models.entities.Doctor;
import com.patientappointment.scheduler.models.entities.Patient;
import com.patientappointment.scheduler.repositories.AppointmentRepository;
import com.patientappointment.scheduler.services.schedule.ScheduleService;
import com.patientappointment.scheduler.utils.enums.AppointmentStatus;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentServiceValidator appointmentServiceValidator;
    private final ScheduleService scheduleService;
    private final ModelMapper modelMapper;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AppointmentServiceValidator appointmentServiceValidator, ScheduleService scheduleService, ModelMapper modelMapper) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentServiceValidator = appointmentServiceValidator;
        this.scheduleService = scheduleService;
        this.modelMapper = modelMapper;
    }

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO, PatientDTO patientDTO, DoctorDTO doctorDTO) {
        appointmentServiceValidator.validateAppointmentDate(appointmentDTO.getAppointmentDate(), doctorDTO);
        appointmentServiceValidator.validateAppointmentTime(appointmentDTO.getAppointmentDate(), appointmentDTO.getAppointmentTime(), doctorDTO);
        scheduleService.removeSlot(appointmentDTO.getAppointmentDate(), appointmentDTO.getAppointmentTime(), doctorDTO.getId());

        appointmentDTO.setDoctor(modelMapper.map(doctorDTO, Doctor.class));
        appointmentDTO.setPatient(modelMapper.map(patientDTO, Patient.class));
        appointmentDTO.setStatus(AppointmentStatus.SCHEDULED);

        Appointment savedAppointment = appointmentRepository.save(modelMapper.map(appointmentDTO, Appointment.class));

        return modelMapper.map(savedAppointment, AppointmentDTO.class);
    }
}