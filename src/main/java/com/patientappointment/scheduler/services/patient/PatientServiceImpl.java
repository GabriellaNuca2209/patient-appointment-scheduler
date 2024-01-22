package com.patientappointment.scheduler.services.patient;

import com.patientappointment.scheduler.exceptions.patient.PatientNotFoundException;
import com.patientappointment.scheduler.models.dtos.*;
import com.patientappointment.scheduler.models.entities.Appointment;
import com.patientappointment.scheduler.models.entities.Patient;
import com.patientappointment.scheduler.repositories.PatientRepository;
import com.patientappointment.scheduler.services.appointment.AppointmentService;
import com.patientappointment.scheduler.services.doctor.DoctorService;
import com.patientappointment.scheduler.services.medical.PatientMedicalService;
import com.patientappointment.scheduler.utils.enums.DoctorLocation;
import com.patientappointment.scheduler.utils.enums.DoctorSpecialization;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.patientappointment.scheduler.utils.enums.AppointmentStatus.*;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientServiceValidation patientServiceValidation;
    private final PatientMedicalService patientMedicalService;
    private final ModelMapper modelMapper;

    public PatientServiceImpl(PatientRepository patientRepository, PatientServiceValidation patientServiceValidation,
                              PatientMedicalService patientMedicalService, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.patientServiceValidation = patientServiceValidation;
        this.patientMedicalService = patientMedicalService;
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

        Patient savedPatient = patientRepository.save(getUpdatedFields(patient, patientUpdateDTO));
        log.info("Patient with id " + id + " was updated in db.");

        return modelMapper.map(savedPatient, PatientDTO.class);
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient with id " + id + " not found"));
        patientRepository.delete(patient);
    }

    @Override
    public List<DoctorDTO> getFilteredDoctors(DoctorSpecialization specialization, DoctorLocation location, Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Patient with id " + patientId + " not found"));
        log.info("Patient {} : {} searched doctors with filter option", patient.getFirstName(), patient.getLastName());

        return patientMedicalService.getFilteredDoctors(specialization, location);
    }

    @Override
    public List<DoctorScheduleDTO> getDoctorSchedules(Long patientId, Long doctorId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Patient with id " + patientId + " not found"));
        log.info("Patient {} : {} searched for doctor's schedule", patient.getFirstName(), patient.getLastName());

        return patientMedicalService.getDoctorSchedules(doctorId);
    }

    @Override
    public List<LocalTime> getAvailableSlots(Long patientId, Long scheduleId, Long doctorId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Patient with id " + patientId + " not found"));

        DoctorScheduleDTO scheduleDTO = patientMedicalService.getSchedule(scheduleId);
        List<Appointment> appointments = getAppointmentsFromSchedule(scheduleDTO, doctorId);
        log.info("Patient {} : {} retrieved available slots", patient.getFirstName(), patient.getLastName());

        return patientMedicalService.getAvailableSlots(scheduleDTO.getWorkingDate(), doctorId, appointments);
    }

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO, Long patientId, Long doctorId) {
        PatientDTO patientDTO = getPatient(patientId);
        DoctorDTO doctorDTO = patientMedicalService.getDoctor(doctorId);

        return patientMedicalService.createAppointment(appointmentDTO, patientDTO, doctorDTO);
    }

    @Override
    public List<AppointmentDTO> getPatientAppointments(Long id) {
        patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient with id " + id + " not found"));

        return patientMedicalService.getPatientAppointments(id);
    }

    @Override
    public AppointmentDTO cancelAppointment(Long patientId, Long appointmentId) {
        patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Patient with id " + patientId + " not found"));

        return patientMedicalService.cancelAppointment(appointmentId);
    }

    private Patient getUpdatedFields(Patient patient, PatientUpdateDTO patientUpdateDTO) {
        if (patientUpdateDTO.getFirstName() != null) {
            patient.setFirstName(patientUpdateDTO.getFirstName());
        }
        if (patientUpdateDTO.getLastName() != null) {
            patient.setLastName(patientUpdateDTO.getLastName());
        }
        if (patientUpdateDTO.getEmail() != null) {
            patient.setEmail(patientUpdateDTO.getEmail());
        }
        return patient;
    }

    private List<Appointment> getAppointmentsFromSchedule(DoctorScheduleDTO scheduleDTO, Long doctorId) {
        List<Appointment> scheduledAppointments = patientMedicalService.getAppointments(scheduleDTO.getWorkingDate(), doctorId, SCHEDULED);
        List<Appointment> openedAppointments = patientMedicalService.getAppointments(scheduleDTO.getWorkingDate(), doctorId, ONGOING);
        List<Appointment> closedAppointments = patientMedicalService.getAppointments(scheduleDTO.getWorkingDate(), doctorId, COMPLETED);

        return Stream.of(scheduledAppointments, openedAppointments, closedAppointments)
                .flatMap(Collection::stream)
                .toList();
    }
}