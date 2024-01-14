package com.patientappointment.scheduler.repositories;

import com.patientappointment.scheduler.models.entities.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<DoctorSchedule, Long> {
    List<DoctorSchedule> findByDoctorId(Long doctorId);
    DoctorSchedule findByWorkingDateAndDoctorId(LocalDate date, Long doctorId);
}
