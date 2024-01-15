package com.patientappointment.scheduler.exceptions;

import com.patientappointment.scheduler.exceptions.appointment.DateNotFoundException;
import com.patientappointment.scheduler.exceptions.appointment.TimeNotFoundException;
import com.patientappointment.scheduler.exceptions.doctor.DoctorAlreadyExistsException;
import com.patientappointment.scheduler.exceptions.doctor.DoctorNotFoundException;
import com.patientappointment.scheduler.exceptions.patient.DobFutureException;
import com.patientappointment.scheduler.exceptions.patient.PatientAlreadyExistsException;
import com.patientappointment.scheduler.exceptions.patient.PatientNotFoundException;
import com.patientappointment.scheduler.exceptions.patient.UnderageException;
import com.patientappointment.scheduler.exceptions.schedule.ScheduleOutOfBoundsException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, Object> result = new HashMap<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(error -> result.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception) {
        Map<String, Object> result = new HashMap<>();

        result.put("message", exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessageTemplate)
                .findFirst());

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "Invalid input");
        return new  ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PatientAlreadyExistsException.class)
    public ResponseEntity<Object> handlePatientAlreadyExistsException(PatientAlreadyExistsException e) {
        return getResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Object> handlePatientNotFoundException(PatientNotFoundException e) {
        return getResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DoctorAlreadyExistsException.class)
    public ResponseEntity<Object> handleDoctorAlreadyExistsException(DoctorAlreadyExistsException e) {
        return getResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<Object> handleDoctorNotFoundException(DoctorNotFoundException e) {
        return getResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TimeNotFoundException.class)
    public ResponseEntity<Object> handleTimeNotFoundException(TimeNotFoundException e) {
        return getResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DateNotFoundException.class)
    public ResponseEntity<Object> handleDateNotFoundException(DateNotFoundException e) {
        return getResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ScheduleOutOfBoundsException.class)
    public ResponseEntity<Object> handleScheduleOutOfBoundsException(ScheduleOutOfBoundsException e) {
        return getResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnderageException.class)
    public ResponseEntity<Object> handleUnderageException(UnderageException e) {
        return getResponse(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DobFutureException.class)
    public ResponseEntity<Object> handleDobFutureException(DobFutureException e) {
        return getResponse(e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> getResponse(RuntimeException e, HttpStatus httpStatus) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", e.getMessage());
        return new ResponseEntity<>(result, httpStatus);
    }
}
