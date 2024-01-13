package com.patientappointment.scheduler.repositories;

import com.patientappointment.scheduler.models.entities.Doctor;
import com.patientappointment.scheduler.utils.enums.DoctorLocation;
import com.patientappointment.scheduler.utils.enums.DoctorSpecialization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long>, CustomDoctorRepository {

    Doctor findByEmail(String email);

    Doctor findBySpecialization(DoctorSpecialization specialization);

    Doctor findByLocation(DoctorLocation location);
}
