package com.patientappointment.scheduler.models.dtos.chat_gpt;

import lombok.Data;

import java.util.List;

@Data
public class ChatGPTResponseDTO {

    List<ChoiceDTO> choices;
}
