package com.patientappointment.scheduler.services.appointment;

import com.patientappointment.scheduler.models.dtos.AppointmentDTO;
import com.patientappointment.scheduler.models.dtos.DoctorDTO;
import com.patientappointment.scheduler.models.dtos.PatientDTO;

public interface AppointmentService {

    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO, PatientDTO patientDTO, DoctorDTO doctorDTO);
}
