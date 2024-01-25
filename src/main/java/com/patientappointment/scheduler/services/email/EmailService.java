package com.patientappointment.scheduler.services.email;

public interface EmailService {

    void sendRegistrationEmail(String userEmail, String userName, Long userId);
}
