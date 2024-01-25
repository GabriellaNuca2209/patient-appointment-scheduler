package com.patientappointment.scheduler.services.doctor;

import com.patientappointment.scheduler.exceptions.doctor.DoctorAlreadyExistsException;
import com.patientappointment.scheduler.exceptions.schedule.ScheduleDateBeforePresent;
import com.patientappointment.scheduler.exceptions.schedule.ScheduleOutOfBoundsException;
import com.patientappointment.scheduler.exceptions.schedule.ScheduleShiftsUnorderedException;
import com.patientappointment.scheduler.models.dtos.DoctorDTO;
import com.patientappointment.scheduler.models.entities.Doctor;
import com.patientappointment.scheduler.repositories.doctor.DoctorRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.patientappointment.scheduler.utils.constants.ScheduleConstants.CLOSING_HOURS;
import static com.patientappointment.scheduler.utils.constants.ScheduleConstants.OPENING_HOURS;

@Component
public class DoctorServiceValidationImpl implements DoctorServiceValidation {

    private final DoctorRepository doctorRepository;

    public DoctorServiceValidationImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public void validateDoctorAlreadyExists(DoctorDTO doctorDTO) {
        Doctor foundDoctor = doctorRepository.findByEmail(doctorDTO.getEmail());

        if (foundDoctor != null) throw new DoctorAlreadyExistsException("Doctor with email " + doctorDTO.getEmail() + " already exists");
    }

    @Override
    public void validateScheduleBetweenWorkingHours(LocalTime start, LocalTime end) {
        if (start.isBefore(LocalTime.parse(OPENING_HOURS)) || end.isAfter(LocalTime.parse(CLOSING_HOURS))
            || end.isBefore(LocalTime.parse(OPENING_HOURS)) || start.isAfter(LocalTime.parse(CLOSING_HOURS))) {
            throw new ScheduleOutOfBoundsException("Time outside working hours");
        }
    }

    @Override
    public void validateScheduleShiftsAreInOrder(LocalTime start, LocalTime end) {
        if (start.isAfter(end)) {
            throw new ScheduleShiftsUnorderedException("Shifts unordered.");
        }
    }

    @Override
    public void validateDateIsNotBeforePresent(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new ScheduleDateBeforePresent("Invalid date.");
        }
    }
}
