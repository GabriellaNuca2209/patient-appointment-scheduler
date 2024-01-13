package com.patientappointment.scheduler.repositories;

import com.patientappointment.scheduler.models.entities.Doctor;
import com.patientappointment.scheduler.utils.enums.DoctorLocation;
import com.patientappointment.scheduler.utils.enums.DoctorSpecialization;

import java.util.List;

public interface CustomDoctorRepository {

    List<Doctor> findFilteredDoctors(DoctorSpecialization specialization, DoctorLocation location);
}
