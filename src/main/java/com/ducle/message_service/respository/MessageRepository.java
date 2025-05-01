package com.ducle.message_service.respository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ducle.message_service.model.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
        @Query("SELECT m FROM Message m WHERE m.roomId = :roomId AND m.id > :lastMessageId ORDER BY m.id ASC")
        List<Message> findAllAfterMessageIdByRoom(@Param("roomId") Long roomId,
                        @Param("lastMessageId") Long lastMessageId,
                        Pageable pageable);

        @Query("SELECT m FROM Message m WHERE m.roomId = :roomId AND m.id < :lastMessageId ORDER BY m.id ASC")
        List<Message> findAllBeforeMessageIdByRoom(@Param("roomId") Long roomId,
                        @Param("lastMessageId") Long lastMessageId,
                        Pageable pageable);

        @Query("SELECT m FROM Message m WHERE m.roomId = :roomId AND m.createdAt > :lastSeen ORDER BY m.createdAt ASC")
        List<Message> findAllAfterTimeStampByCreatedAt(@Param("roomId") Long roomId,
                        @Param("lastSeen") Instant lastSeen,
                        Pageable pageable);

        @Query("SELECT COUNT(m) > 0 FROM Message m WHERE m.roomId = :roomId AND m.createdAt < :lastSeen")
        Boolean existsBeforeTimeStampByCreatedAt(@Param("roomId") Long roomId,
                        @Param("lastSeen") Instant lastSeen);

        @Query("SELECT m FROM Message m WHERE m.roomId = :roomId ORDER BY m.id DESC")
        List<Message> findLatestMessagesByRoom(@Param("roomId") Long roomId, Pageable pageable);
}
