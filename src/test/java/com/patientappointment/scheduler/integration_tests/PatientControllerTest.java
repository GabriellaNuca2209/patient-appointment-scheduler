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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        PatientDTO patientDTO = PatientDTO.builder()
                .firstName("Jane")
                .lastName("Doe")
                .dob(LocalDate.of(2000, 9, 22))
                .email("jane@email.com")
                .build();

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
        assertEquals(patientDTO.getEmail(), patientDTOConverted.getEmail());
    }

    @Test
    void test_CreatePatient_ShouldFail_IfPatientWithSameEmailAlreadyExists() throws Exception {
        PatientDTO patientDTO = PatientDTO.builder()
                .firstName("Jane")
                .lastName("Doe")
                .dob(LocalDate.of(2000, 9, 22))
                .email("jane@email.com")
                .build();

        PatientDTO secondPatientDTO = PatientDTO.builder()
                .firstName("Mark")
                .lastName("Wane")
                .dob(LocalDate.of(1967, 10, 15))
                .email("jane@email.com")
                .build();

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

    @Test
    void test_GetAllPatients_ShouldPass() throws Exception {
        PatientDTO patientDTO = PatientDTO.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Doe")
                .dob(LocalDate.of(2000, 9, 22))
                .email("jane@email.com")
                .build();

        PatientDTO secondPatientDTO = PatientDTO.builder()
                .id(2L)
                .firstName("John")
                .lastName("Doe")
                .dob(LocalDate.of(1999, 9, 22))
                .email("john@email.com")
                .build();

        List<PatientDTO> patientDTOList = new ArrayList<>(List.of(patientDTO, secondPatientDTO));

        mockMvc.perform(post("/api/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientDTO)));

        mockMvc.perform(post("/api/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(secondPatientDTO)));

        MvcResult result = mockMvc.perform(get("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDTOList)))
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = result.getResponse().getContentAsString();
        List<PatientDTO> patientDTOListConverted = objectMapper.readerForListOf(PatientDTO.class).readValue(resultAsString);

        assertEquals(patientDTOList, patientDTOListConverted);
    }
}
