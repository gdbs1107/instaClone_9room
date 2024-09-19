package com.example.instaclone_9room.repository.DmRepository;

import com.example.instaclone_9room.domain.DM.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByChatPartId(Long chatPartId);
}
