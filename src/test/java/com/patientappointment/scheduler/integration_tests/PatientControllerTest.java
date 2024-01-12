package com.patientappointment.scheduler.integration_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patientappointment.scheduler.models.dtos.PatientDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Transactional
@AutoConfigureTestDatabase
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_CreatePatient_ShouldPass() throws Exception {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setFirstName("Jane");
        patientDTO.setLastName("Doe");
        patientDTO.setDob(LocalDate.of(2000, 9, 22));
        patientDTO.setAge((int) ChronoUnit.YEARS.between(patientDTO.getDob(), LocalDate.now()));
        patientDTO.setEmail("jane@email.com");

        MvcResult result = mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = result.getResponse().getContentAsString();
        PatientDTO patientDTOConverted = objectMapper.readValue(resultAsString, PatientDTO.class);

        log.info("PatientDTO result: " + patientDTOConverted);

        assertEquals(patientDTO.getFirstName(), patientDTOConverted.getFirstName());
        assertEquals(patientDTO.getLastName(), patientDTOConverted.getLastName());
        assertEquals(patientDTO.getDob(), patientDTOConverted.getDob());
        assertEquals(patientDTO.getAge(), patientDTOConverted.getAge());
        assertEquals(patientDTO.getEmail(), patientDTOConverted.getEmail());
    }

    @Test
    void test_CreatePatient_ShouldFail_IfPatientWithSameEmailAlreadyExists() throws Exception {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setFirstName("Jane");
        patientDTO.setLastName("Doe");
        patientDTO.setDob(LocalDate.of(2000, 9, 22));
        patientDTO.setAge((int) ChronoUnit.YEARS.between(patientDTO.getDob(), LocalDate.now()));
        patientDTO.setEmail("jane@email.com");

        PatientDTO secondPatientDTO = new PatientDTO();
        secondPatientDTO.setFirstName("Mark");
        secondPatientDTO.setLastName("Wane");
        secondPatientDTO.setDob(LocalDate.of(1967, 10, 15));
        secondPatientDTO.setAge((int) ChronoUnit.YEARS.between(patientDTO.getDob(), LocalDate.now()));
        secondPatientDTO.setEmail("jane@email.com");

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDTO)));

        MvcResult result = mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(secondPatientDTO)))
                .andExpect(status().isConflict())
                .andReturn();

        String resultAsString = result.getResponse().getContentAsString();
        log.info(resultAsString);

        assertTrue(resultAsString.contains("Patient with email " + secondPatientDTO.getEmail() + " already exists"));
    }
}
