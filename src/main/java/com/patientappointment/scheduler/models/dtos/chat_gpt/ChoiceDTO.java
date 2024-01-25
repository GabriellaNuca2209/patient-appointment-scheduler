package com.patientappointment.scheduler.models.dtos.chat_gpt;

import lombok.Data;

@Data
public class ChoiceDTO {

    private int index;
    private MessageDTO message;
}
