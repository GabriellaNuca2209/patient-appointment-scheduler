# Patient Appointment Scheduler

A system that ensures efficient and convenient healthcare access for both patients and healthcare providers.

---

## Interactive Features
- **Email Notification**: Patients are able to recieve confirmations through an email service.
- **OpenAI**: Patients can communicate with an AI assistant about health inquiries.

## Key Functionalities
1. Account Creation:
   
   Patients and Doctors can create accounts on the platform.
3. Schedule:

   Doctors are able to create or remove schedules based on their preferences.
3. Filtering:

   Patients are able to search for doctors based on their speciality and working location.
4. Appointment:

   Patients are able to schedule, view or cancel appointments.

5. Consultation

   Doctors can open consultations and mark them as complete at the end.

## Tech stack
- Java 17
- Spring Boot 3.2.1
- Maven
- Hibernate
- PostgreSQL
- Lombok
- Mockito
- SendGrid

## Helpers
1. **Database connection**

  Make sure to create and add your database and credentials.

```
# Database connection properties
spring.datasource.url=jdbc:postgresql://localhost:5432/YOUR_DATABASE
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.datasource.driver-class-name=org.postgresql.Driver
```
2. **OpenAI properties**

  Make sure to create and add your openAI key.

```
# ChatGPT properties
openai.api.key=YOUR_OPENAI_KEY
openai.api.url=https://api.openai.com/v1/chat/completions
openai.api.model=gpt-3.5-turbo
```
3. **SendGrid properties**

  Make sure to configure your SendGrid workspace.

```
# SendGrid properties
app.sendgrid.key=YOUR_SENDGRID_KEY
app.sendgrid.templateId=YOUR_TEMPLATE_ID
email.from=YOUR_SENDGRID_EMAIL
```
