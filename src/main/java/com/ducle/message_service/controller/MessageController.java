package com.ducle.message_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ducle.message_service.model.dto.MessageDTO;
import com.ducle.message_service.service.MessageService;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("${api.messages-url}")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/rooms/{chatRoomId}/last_message_id/{messageId}")
    public ResponseEntity<List<MessageDTO>> getMethodName(@Min(1) @PathVariable Long chatRoomId,
            @Min(1) @PathVariable Long messageId) {
        return ResponseEntity.ok(messageService.getMessagesByLastMessageId(chatRoomId, messageId));
    }

}
