package com.patientappointment.scheduler.repositories;

import com.patientappointment.scheduler.models.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByEmail(String email);
}
