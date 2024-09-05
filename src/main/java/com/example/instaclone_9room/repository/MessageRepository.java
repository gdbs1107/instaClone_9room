package com.example.instaclone_9room.repository;

import com.example.instaclone_9room.domain.DM.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
