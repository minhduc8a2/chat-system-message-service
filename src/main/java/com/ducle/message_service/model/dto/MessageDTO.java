package com.ducle.message_service.model.dto;

import java.io.Serializable;
import java.time.Instant;

import com.ducle.message_service.model.enums.MessageType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MessageDTO(

        Long senderId,
        @NotNull @Min(1) Long roomId,
        @NotBlank String content,
        MessageType type,
        Instant createdAt

) implements Serializable {

}
