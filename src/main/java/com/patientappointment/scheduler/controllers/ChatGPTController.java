package com.patientappointment.scheduler.controllers;

import com.patientappointment.scheduler.models.dtos.chat_gpt.SearchRequestDTO;
import com.patientappointment.scheduler.services.chat_gpt.ChatGPTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/chat")
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    public ChatGPTController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }

    @PostMapping("/{patientId}")
    public ResponseEntity<String> chatWithAI(@RequestBody SearchRequestDTO searchRequestDTO, @PathVariable Long patientId) {
        log.info("User query: " + searchRequestDTO.getQuery());

        return ResponseEntity.ok(chatGPTService.processSearch(searchRequestDTO.getQuery(), patientId));
    }
}
