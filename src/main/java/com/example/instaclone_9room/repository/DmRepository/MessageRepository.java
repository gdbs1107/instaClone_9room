package com.example.instaclone_9room.repository.DmRepository;

import com.example.instaclone_9room.domain.DM.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
