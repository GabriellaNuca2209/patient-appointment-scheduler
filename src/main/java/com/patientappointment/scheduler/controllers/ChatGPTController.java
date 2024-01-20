package com.patientappointment.scheduler.controllers;

import com.patientappointment.scheduler.models.dtos.chat_gpt.SearchRequestDTO;
import com.patientappointment.scheduler.services.chat_gpt.ChatGPTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/chat")
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    public ChatGPTController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }

    @PostMapping
    public ResponseEntity<String> chatWithAI(@RequestBody SearchRequestDTO searchRequestDTO) {
        log.info("User query: " + searchRequestDTO.getQuery());

        return ResponseEntity.ok(chatGPTService.processSearch(searchRequestDTO.getQuery()));
    }
}
