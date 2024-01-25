package com.patientappointment.scheduler.repositories.doctor;

import com.patientappointment.scheduler.models.entities.Doctor;
import com.patientappointment.scheduler.utils.enums.DoctorLocation;
import com.patientappointment.scheduler.utils.enums.DoctorSpecialization;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class CustomDoctorRepositoryImpl implements CustomDoctorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Doctor> findFilteredDoctors(DoctorSpecialization specialization, DoctorLocation location) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);

        Root<Doctor> doctor = criteriaQuery.from(Doctor.class);
        List<Predicate> predicates = new ArrayList<>();

        if (specialization != null) {
            predicates.add(criteriaBuilder.equal(doctor.get("specialization"), specialization));
        }
        if (location != null) {
            predicates.add(criteriaBuilder.equal(doctor.get("location"), location));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
