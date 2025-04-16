package com.ducle.message_service.service.queue_consumer;

import com.ducle.message_service.model.dto.MessageDTO;

public interface QueueConsumer {
    public void consume(MessageDTO message) ;
}
