package com.ducle.message_service.mapper;

import org.mapstruct.Mapper;

import com.ducle.message_service.model.dto.MessageDTO;
import com.ducle.message_service.model.entity.Message;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    
    MessageDTO toMessageDTO(Message message);

    Message toMessage(MessageDTO messageDTO);
}
