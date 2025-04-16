package com.ducle.message_service.service.queue_consumer.impl;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ducle.message_service.model.dto.MessageDTO;
import com.ducle.message_service.service.queue_consumer.QueueConsumer;

@Service
public class KafkaConsumer implements QueueConsumer {

    @Override
    @KafkaListener(topics = "${kafka-config.topic-name}", groupId = "chat-group")
    public void consume(MessageDTO message) {
        
    }
}
