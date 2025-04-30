package com.ducle.message_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ducle.message_service.mapper.MessageMapper;
import com.ducle.message_service.model.dto.MessageDTO;
import com.ducle.message_service.model.entity.Message;
import com.ducle.message_service.respository.MessageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public void saveMessage(MessageDTO messageDTO) {
        Message message = messageMapper.toMessage(messageDTO);
        messageRepository.save(message);
    }

    public List<MessageDTO> getMessagesByLastMessageId(Long chatRoomId, Long lastMessageId) {
        final int SIZE = 20;
        Pageable pageable = PageRequest.of(0, SIZE);
        List<Message> messages = messageRepository.findAllAfterMessageIdByRoom(chatRoomId, lastMessageId, pageable);

        return messages.stream()
                .map(messageMapper::toMessageDTO)
                .collect(Collectors.toList());
    }
}
