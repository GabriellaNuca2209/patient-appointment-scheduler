package com.patientappointment.scheduler.models.dtos.chat_gpt;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatGPTRequestDTO {

    private String model;
    private List<MessageDTO> messages = new ArrayList<>();
}
