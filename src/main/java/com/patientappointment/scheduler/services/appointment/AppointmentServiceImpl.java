package com.patientappointment.scheduler.services.appointment;

import com.patientappointment.scheduler.exceptions.appointment.AppointmentNotFoundException;
import com.patientappointment.scheduler.models.dtos.AppointmentDTO;
import com.patientappointment.scheduler.models.dtos.DoctorDTO;
import com.patientappointment.scheduler.models.dtos.PatientDTO;
import com.patientappointment.scheduler.models.entities.Appointment;
import com.patientappointment.scheduler.models.entities.Doctor;
import com.patientappointment.scheduler.models.entities.Patient;
import com.patientappointment.scheduler.repositories.AppointmentRepository;
import com.patientappointment.scheduler.utils.enums.AppointmentStatus;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentServiceValidation appointmentServiceValidation;
    private final ModelMapper modelMapper;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AppointmentServiceValidation appointmentServiceValidation, ModelMapper modelMapper) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentServiceValidation = appointmentServiceValidation;
        this.modelMapper = modelMapper;
    }


    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO, PatientDTO patientDTO, DoctorDTO doctorDTO) {
        appointmentServiceValidation.validateAppointmentDate(appointmentDTO.getAppointmentDate(), doctorDTO.getId());
        appointmentServiceValidation.validateAppointmentTime(appointmentDTO.getAppointmentDate(), appointmentDTO.getAppointmentTime(), doctorDTO.getId());

        appointmentDTO.setDoctor(modelMapper.map(doctorDTO, Doctor.class));
        appointmentDTO.setPatient(modelMapper.map(patientDTO, Patient.class));
        appointmentDTO.setStatus(AppointmentStatus.SCHEDULED);

        Appointment savedAppointment = appointmentRepository.save(modelMapper.map(appointmentDTO, Appointment.class));

        return modelMapper.map(savedAppointment, AppointmentDTO.class);
    }

    @Override
    public List<Appointment> getAppointments(LocalDate date, Long doctorId, AppointmentStatus status) {
        return appointmentRepository.findByAppointmentDateAndDoctorIdAndStatus(date, doctorId, status);
    }

    @Override
    public List<AppointmentDTO> getPatientAppointments(Long id) {
        return appointmentRepository.findByPatientId(id).stream().map(app -> modelMapper.map(app, AppointmentDTO.class)).toList();
    }

    @Override
    public AppointmentDTO cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id " + appointmentId + " not found"));
        appointment.setStatus(AppointmentStatus.CANCELED);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        return modelMapper.map(savedAppointment, AppointmentDTO.class);
    }
}