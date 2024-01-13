package com.patientappointment.scheduler.repositories;

import com.patientappointment.scheduler.models.entities.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<DoctorSchedule, Long> {
}
