package com.patientappointment.scheduler.services.email;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${app.sendgrid.email.from}")
    private String fromEmail;
    @Value("${app.sendgrid.key}")
    private String key;
    @Value("${app.sendgrid.templateId}")
    private String template;

    @Override
    public void sendRegistrationEmail(String userEmail, String userName, Long userId) {
        SendGrid sendGrid = new SendGrid(key);
        Mail registrationEmail = prepareMail(userEmail, userName, userId);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(registrationEmail.build());

            Response response = sendGrid.api(request);

            log.info(response.getBody());
            log.info(response.getHeaders().toString());
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    private Mail prepareMail(String userEmail, String userName, Long userId) {
        Email from = new Email(fromEmail);
        Email to = new Email(userEmail);

        Mail mail = new Mail();
        Personalization personalization = new Personalization();

        personalization.addTo(to);
        personalization.addDynamicTemplateData("name", userName);
        personalization.addDynamicTemplateData("id", userId);

        mail.setFrom(from);
        mail.setTemplateId(template);
        mail.addPersonalization(personalization);

        return mail;
    }
}