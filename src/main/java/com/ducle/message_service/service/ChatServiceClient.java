package com.ducle.message_service.service;

import java.time.Instant;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("${chat-service.name}")
public interface ChatServiceClient {
    @GetMapping("/internal/chat/room_last_seen")
    Instant getRoomLastSeenTimeStamp(@RequestParam Long chatRoomId, @RequestParam Long userId);
}
