package com.patientappointment.scheduler.repositories;

import com.patientappointment.scheduler.models.entities.Appointment;
import com.patientappointment.scheduler.utils.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByAppointmentDateAndDoctorIdAndStatus(LocalDate date, Long id, AppointmentStatus status);
    List<Appointment> findByPatientId(Long id);
}
