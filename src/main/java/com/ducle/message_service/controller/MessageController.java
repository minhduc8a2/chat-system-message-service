package com.ducle.message_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ducle.message_service.model.dto.InfiniteScrollResult;
import com.ducle.message_service.model.dto.MessageDTO;
import com.ducle.message_service.model.enums.InfiniteLoadType;
import com.ducle.message_service.service.MessageService;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("${api.messages-url}")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<InfiniteScrollResult<MessageDTO>> getMethodName(@Min(1) @RequestParam Long chatRoomId,
            @Min(1) @RequestParam Long messageId, @NotBlank String type) {
        return ResponseEntity.ok(messageService.getMessagesByLastMessageId(chatRoomId, messageId, type));
    }

}
