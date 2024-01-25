package com.patientappointment.scheduler.services.chat_gpt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patientappointment.scheduler.models.dtos.PatientDTO;
import com.patientappointment.scheduler.models.dtos.chat_gpt.ChatGPTRequestDTO;
import com.patientappointment.scheduler.models.dtos.chat_gpt.ChatGPTResponseDTO;
import com.patientappointment.scheduler.models.dtos.chat_gpt.MessageDTO;
import com.patientappointment.scheduler.services.patient.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChatGPTServiceImpl implements ChatGPTService {

    @Value("${openai.api.url}")
    private String url;
    @Value("${openai.api.model}")
    private String model;
    @Value("${openai.api.key}")
    private String key;

    private final ObjectMapper objectMapper;
    private final PatientService patientService;

    public ChatGPTServiceImpl(ObjectMapper objectMapper, PatientService patientService) {
        this.objectMapper = objectMapper;
        this.patientService = patientService;
    }

    @Override
    public String processSearch(String query, Long patientId) {
        PatientDTO patientDTO =  patientService.getPatient(patientId);
        ChatGPTRequestDTO chatGPTRequestDTO = new ChatGPTRequestDTO();
        chatGPTRequestDTO.setModel(model);
        chatGPTRequestDTO.getMessages().add(new MessageDTO("system", "You are a medical assistant. " +
                "Your role is to help patients choose the correct medical specialization based on their problem. " +
                "Please address him with the name " + patientDTO.getFirstName() + " Please answer using up to 100 tokens."));

        chatGPTRequestDTO.getMessages().add(new MessageDTO("user", query));

        return getResponse(chatGPTRequestDTO);
    }

    private String getResponse(ChatGPTRequestDTO chatGPTRequestDTO) {
        try (CloseableHttpClient httpClient = HttpClients.custom().build();
             CloseableHttpResponse response = httpClient.execute(getPost(chatGPTRequestDTO))) {

            String responseBody = EntityUtils.toString(response.getEntity());

            log.info("Response body: " + responseBody);
            ChatGPTResponseDTO chatGPTResponse = objectMapper.readValue(responseBody, ChatGPTResponseDTO.class);
            return chatGPTResponse.getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private HttpPost getPost(ChatGPTRequestDTO chatGPTRequestDTO) throws Exception {
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer " + key);

        String body = objectMapper.writeValueAsString(chatGPTRequestDTO);
        log.info("Body: " + body);

        StringEntity entity = new StringEntity(body);
        post.setEntity(entity);

        return post;
    }
}