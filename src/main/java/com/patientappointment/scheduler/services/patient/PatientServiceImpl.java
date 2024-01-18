package com.patientappointment.scheduler.services.patient;

import com.patientappointment.scheduler.exceptions.patient.PatientNotFoundException;
import com.patientappointment.scheduler.models.dtos.*;
import com.patientappointment.scheduler.models.entities.Appointment;
import com.patientappointment.scheduler.models.entities.Patient;
import com.patientappointment.scheduler.repositories.PatientRepository;
import com.patientappointment.scheduler.services.appointment.AppointmentService;
import com.patientappointment.scheduler.services.doctor.DoctorService;
import com.patientappointment.scheduler.utils.enums.DoctorLocation;
import com.patientappointment.scheduler.utils.enums.DoctorSpecialization;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.patientappointment.scheduler.utils.enums.AppointmentStatus.*;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientServiceValidation patientServiceValidation;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final ModelMapper modelMapper;

    public PatientServiceImpl(PatientRepository patientRepository, PatientServiceValidation patientServiceValidation, DoctorService doctorService,
                              AppointmentService appointmentService, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.patientServiceValidation = patientServiceValidation;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.modelMapper = modelMapper;
    }

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        patientServiceValidation.validatePatientAlreadyExists(patientDTO);
        patientServiceValidation.validatePatientDob(patientDTO.getDob());

        Patient savedPatient = patientRepository.save(modelMapper.map(patientDTO, Patient.class));
        log.info("Patient " + savedPatient.getFirstName() + " " + savedPatient.getLastName() + " was saved in database.");

        return modelMapper.map(savedPatient, PatientDTO.class);
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream().map(patient -> modelMapper.map(patient, PatientDTO.class)).toList();
    }

    @Override
    public PatientDTO getPatient(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient with id " + id + " not found"));

        return modelMapper.map(patient, PatientDTO.class);
    }

    @Override
    public PatientDTO updatePatient(Long id, PatientUpdateDTO patientUpdateDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient with id " + id + " not found"));

        if (patientUpdateDTO.getFirstName() != null) {
            patient.setFirstName(patientUpdateDTO.getFirstName());
        }
        if (patientUpdateDTO.getLastName() != null) {
            patient.setLastName(patientUpdateDTO.getLastName());
        }
        if (patientUpdateDTO.getEmail() != null) {
            patient.setEmail(patientUpdateDTO.getEmail());
        }

        Patient savedPatient = patientRepository.save(patient);
        log.info("Patient with id " + id + " was updated in db.");

        return modelMapper.map(savedPatient, PatientDTO.class);
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient with id " + id + " not found"));
        patientRepository.delete(patient);
    }

    @Override
    public List<DoctorDTO> getFilteredDoctors(DoctorSpecialization specialization, DoctorLocation location) {
        return doctorService.getFilteredDoctors(specialization, location);
    }

    @Override
    public List<DoctorScheduleDTO> getDoctorSchedules(Long doctorId) {
        return doctorService.getDoctorSchedules(doctorId);
    }

    @Override
    public List<LocalTime> getAvailableSlots(Long patientId, Long scheduleId, Long doctorId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Patient with id " + patientId + " not found"));
        DoctorScheduleDTO scheduleDTO = doctorService.getDoctorSchedule(scheduleId);
        List<Appointment> scheduledAppointments = appointmentService.getAppointments(scheduleDTO.getWorkingDate(), doctorId, SCHEDULED);
        List<Appointment> openedAppointments = appointmentService.getAppointments(scheduleDTO.getWorkingDate(), doctorId, ONGOING);
        List<Appointment> closedAppointments = appointmentService.getAppointments(scheduleDTO.getWorkingDate(), doctorId, COMPLETED);

        List<Appointment> appointments = new ArrayList<>(scheduledAppointments);
        appointments.addAll(openedAppointments);
        appointments.addAll(closedAppointments);
        log.info("Patient {} : {} retrieved available slots", patient.getFirstName(), patient.getLastName());

        return doctorService.getAvailableSlots(scheduleDTO.getWorkingDate(), doctorId, appointments);
    }

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO, Long patientId, Long doctorId) {
        PatientDTO patientDTO = getPatient(patientId);
        DoctorDTO doctorDTO = doctorService.getDoctor(doctorId);

        return appointmentService.createAppointment(appointmentDTO, patientDTO, doctorDTO);
    }

    @Override
    public List<AppointmentDTO> getPatientAppointments(Long id) {
        patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient with id " + id + " not found"));

        return appointmentService.getPatientAppointments(id);
    }

    @Override
    public AppointmentDTO cancelAppointment(Long patientId, Long appointmentId) {
        patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Patient with id " + patientId + " not found"));

        return appointmentService.cancelAppointment(appointmentId);
    }
}