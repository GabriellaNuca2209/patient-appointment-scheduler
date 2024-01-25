package com.patientappointment.scheduler.services.doctor;

import com.patientappointment.scheduler.exceptions.doctor.DoctorNotFoundException;
import com.patientappointment.scheduler.models.dtos.AppointmentDTO;
import com.patientappointment.scheduler.models.dtos.DoctorDTO;
import com.patientappointment.scheduler.models.dtos.DoctorScheduleDTO;
import com.patientappointment.scheduler.models.entities.Appointment;
import com.patientappointment.scheduler.models.entities.Doctor;
import com.patientappointment.scheduler.repositories.doctor.DoctorRepository;
import com.patientappointment.scheduler.services.medical.doctor.DoctorMedicalService;
import com.patientappointment.scheduler.utils.enums.DoctorLocation;
import com.patientappointment.scheduler.utils.enums.DoctorSpecialization;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.patientappointment.scheduler.utils.enums.AppointmentStatus.SCHEDULED;

@Slf4j
@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorServiceValidation doctorServiceValidation;
    private final DoctorMedicalService doctorMedicalService;
    private final ModelMapper modelMapper;

    public DoctorServiceImpl(DoctorRepository doctorRepository, DoctorServiceValidation doctorServiceValidation, DoctorMedicalService doctorMedicalService, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.doctorServiceValidation = doctorServiceValidation;
        this.doctorMedicalService = doctorMedicalService;
        this.modelMapper = modelMapper;
    }

    @Override
    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        doctorServiceValidation.validateDoctorAlreadyExists(doctorDTO);
        Doctor savedDoctor = doctorRepository.save(modelMapper.map(doctorDTO, Doctor.class));
        log.info("Doctor " + doctorDTO.getFirstName() + " " + doctorDTO.getLastName() + " was saved in database");

        return modelMapper.map(savedDoctor, DoctorDTO.class);
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream().map(doctor -> modelMapper.map(doctor, DoctorDTO.class)).toList();
    }

    @Override
    public DoctorDTO getDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new DoctorNotFoundException("Doctor with id: " + id + " not found"));

        return modelMapper.map(doctor, DoctorDTO.class);
    }

    @Override
    public DoctorScheduleDTO createSchedule(DoctorScheduleDTO doctorScheduleDTO, Long doctorId) {
        doctorServiceValidation.validateScheduleBetweenWorkingHours(doctorScheduleDTO.getStartShift(), doctorScheduleDTO.getEndShift());
        doctorServiceValidation.validateScheduleShiftsAreInOrder(doctorScheduleDTO.getStartShift(), doctorScheduleDTO.getEndShift());
        doctorServiceValidation.validateDateIsNotBeforePresent(doctorScheduleDTO.getWorkingDate());

        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Doctor with id: " + doctorId + " not found"));
        doctorScheduleDTO.setDoctor(doctor);

        return doctorMedicalService.createSchedule(doctorScheduleDTO);
    }

    @Override
    public void deleteSchedule(Long doctorId, Long scheduleId) {
        doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Doctor with id: " + doctorId + " not found"));
        log.info("Doctor with id " + doctorId + " deleted schedule with id " + scheduleId);
        cancelDoctorAppointments(doctorId, scheduleId);

        doctorMedicalService.deleteSchedule(scheduleId);
    }

    @Override
    public List<DoctorDTO> getFilteredDoctors(DoctorSpecialization specialization, DoctorLocation location) {
        if (specialization == null && location == null) {
            return getAllDoctors();
        }

        return doctorRepository.findFilteredDoctors(specialization, location).stream().map(doctor -> modelMapper.map(doctor, DoctorDTO.class)).toList();
    }

    @Override
    public List<DoctorScheduleDTO> getDoctorSchedules(Long doctorId) {
        doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Doctor with id: " + doctorId + " not found"));

        return doctorMedicalService.getDoctorSchedules(doctorId);
    }

    @Override
    public AppointmentDTO openConsultation(Long doctorId, Long appointmentId) {
        doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Doctor with id: " + doctorId + " not found"));

        return doctorMedicalService.openConsultation(appointmentId);
    }

    @Override
    public AppointmentDTO closeConsultation(Long doctorId, Long appointmentId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Doctor with id: " + doctorId + " not found"));
        AppointmentDTO appointmentDTO = doctorMedicalService.closeConsultation(appointmentId);
        doctor.getPatients().add(appointmentDTO.getPatient());
        doctorRepository.save(doctor);
        log.info("patients for doctor: " + doctor.getPatients());

        return appointmentDTO;
    }

    private void cancelDoctorAppointments(Long doctorId, Long scheduleId) {
        DoctorScheduleDTO doctorScheduleDTO = doctorMedicalService.getSchedule(scheduleId);
        List<Appointment> appointments = doctorMedicalService.getAppointments(doctorScheduleDTO.getWorkingDate(), doctorId, SCHEDULED);

        for (Appointment appointment : appointments) {
            doctorMedicalService.cancelDoctorAppointment(appointment);
        }
    }
}