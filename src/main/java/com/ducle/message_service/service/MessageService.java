package com.ducle.message_service.service;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ducle.message_service.mapper.MessageMapper;
import com.ducle.message_service.model.dto.InfiniteScrollResult;
import com.ducle.message_service.model.dto.MessageDTO;
import com.ducle.message_service.model.entity.Message;
import com.ducle.message_service.model.enums.InfiniteLoadType;
import com.ducle.message_service.respository.MessageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final ChatServiceClient chatServiceClient;

    public void saveMessage(MessageDTO messageDTO) {
        Message message = messageMapper.toMessage(messageDTO);
        messageRepository.save(message);
    }

    public InfiniteScrollResult<MessageDTO> getMessagesByLastMessageId(Long chatRoomId, Long lastMessageId,
            String strType) {

        InfiniteLoadType type;
        try {
            type = InfiniteLoadType.valueOf(strType.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("type must be \"top\" or \"bottom\"");
        }
        final int SIZE = 20;
        Pageable pageable = PageRequest.of(0, SIZE + 1);
        List<Message> messages = type.equals(InfiniteLoadType.BOTTOM)
                ? messageRepository.findAllAfterMessageIdByRoom(chatRoomId, lastMessageId, pageable)
                : messageRepository.findAllBeforeMessageIdByRoom(chatRoomId, lastMessageId, pageable);

        boolean hasMore = messages.size() > SIZE;

        if (hasMore) {
            messages = messages.subList(0, SIZE); // Trim the extra one
        }

        List<MessageDTO> dtoList = messages.stream()
                .map(messageMapper::toMessageDTO)
                .toList();

        return new InfiniteScrollResult<>(dtoList, type.equals(InfiniteLoadType.TOP) && hasMore,
                type.equals(InfiniteLoadType.BOTTOM) && hasMore);
    }

    public InfiniteScrollResult<MessageDTO> getMessagesByRoomLastSeen(Long chatRoomId, Long userId) {

        final int SIZE = 20;
        Pageable pageable = PageRequest.of(0, SIZE + 1);
        Instant roomLastSeen = chatServiceClient.getRoomLastSeenTimeStamp(chatRoomId, userId);
        boolean hasMoreOnTop = messageRepository.existsBeforeTimeStampByCreatedAt(chatRoomId, roomLastSeen);
        List<Message> messages = messageRepository.findAllAfterTimeStampByCreatedAt(chatRoomId, roomLastSeen, pageable);

        boolean hasMoreBottom = messages.size() > SIZE;

        if (hasMoreBottom) {
            messages = messages.subList(0, SIZE); // Trim the extra one
        }

        List<MessageDTO> dtoList = messages.stream()
                .map(messageMapper::toMessageDTO)
                .toList();

        return new InfiniteScrollResult<>(dtoList, hasMoreOnTop, hasMoreBottom);
    }
}
