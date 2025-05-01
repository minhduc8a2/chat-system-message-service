package com.ducle.message_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ducle.message_service.model.dto.InfiniteScrollResult;
import com.ducle.message_service.model.dto.MessageDTO;
import com.ducle.message_service.service.MessageService;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("${api.messages-url}")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<InfiniteScrollResult<MessageDTO>> getInfiniteMessages(@Min(1) @RequestParam Long chatRoomId,
            @Nullable @RequestParam Long messageId, @NotBlank String type) {
        return ResponseEntity.ok(messageService.getMessagesByLastMessageId(chatRoomId, messageId, type));
    }

    @GetMapping("/last_seen")
    public ResponseEntity<InfiniteScrollResult<MessageDTO>> getMessagesFromLastSeen(
            @Min(1) @RequestParam Long chatRoomId,
            @RequestHeader("X-User-UserId") Long userId) {
        return ResponseEntity.ok(messageService.getMessagesByRoomLastSeen(chatRoomId, userId));
    }

}
