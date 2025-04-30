package com.ducle.message_service.service;

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
}
