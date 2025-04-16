package com.ducle.message_service.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ducle.message_service.model.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
