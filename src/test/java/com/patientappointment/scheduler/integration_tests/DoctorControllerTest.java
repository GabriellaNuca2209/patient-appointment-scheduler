package com.patientappointment.scheduler.integration_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patientappointment.scheduler.models.dtos.DoctorDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.patientappointment.scheduler.utils.enums.DoctorLocation.CLINIC_A;
import static com.patientappointment.scheduler.utils.enums.DoctorSpecialization.CARDIOLOGY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Transactional
@AutoConfigureTestDatabase
@ActiveProfiles("dev")
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_CreateDoctor_ShouldPass() throws Exception {
        DoctorDTO doctorDTO = DoctorDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@email.com")
                .specialization(CARDIOLOGY)
                .location(CLINIC_A)
                .build();

        MvcResult result = mockMvc.perform(post("/api/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctorDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = result.getResponse().getContentAsString();
        DoctorDTO doctorDTOConverted = objectMapper.readValue(resultAsString, DoctorDTO.class);

        log.info("DoctorDTO result: " + doctorDTOConverted);

        assertEquals(doctorDTO.getFirstName(), doctorDTOConverted.getFirstName());
        assertEquals(doctorDTO.getLastName(), doctorDTOConverted.getLastName());
        assertEquals(doctorDTO.getEmail(), doctorDTOConverted.getEmail());
        assertEquals(doctorDTO.getSpecialization(), doctorDTOConverted.getSpecialization());
        assertEquals(doctorDTO.getLocation(), doctorDTOConverted.getLocation());
    }
}
